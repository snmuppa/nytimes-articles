package com.fetherz.saim.nytimessearch.models.search.settings;

import android.text.TextUtils;

import com.fetherz.saim.nytimessearch.utils.LogUtil;

import java.io.InvalidObjectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.fetherz.saim.nytimessearch.models.search.settings.SortOrder.OLDEST;

/**
 * Created by sm032858 on 3/17/17.
 */
public class FilterSelection {

    private static FilterSelection instance = null;

    private FilterSelection(){
        arts = false;
        fashion = false;
        sports = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        beginDate = dateFormat.format(new Date());
        sortOrder = SortOrder.OLDEST;
    }

    public static FilterSelection getInstance(){
        if(instance == null)
        {
            instance = new FilterSelection();
        }
        return instance;
    }

    private boolean arts;
    private boolean fashion;
    private boolean sports;

    private String beginDate;

    private SortOrder sortOrder;

    public boolean isArtsSelected() {
        return arts;
    }

    public void setArts(boolean arts) {
        this.arts = arts;
    }

    public boolean isFashionSelected() {
        return fashion;
    }

    public void setFashion(boolean fashion) {
        this.fashion = fashion;
    }

    public boolean isSportsSelected() {
        return this.sports;
    }

    public void setSports(boolean sports) {
        this.sports = sports;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getBeginDateQueryParam() throws InvalidObjectException {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate;
        try {
            startDate = df.parse(beginDate);
            df = new SimpleDateFormat("yyyyMMdd");
            return df.format(startDate);
        } catch (ParseException e) {
            LogUtil.logE("DATE_PARSE_ERROR", e.getMessage());
            throw new InvalidObjectException("Invalid date format.");
        }
    }

    public String getBeginDateMMddyyyy(){
        return beginDate;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int spinnerPosition) {
        switch (spinnerPosition){
            case 1:
                this.sortOrder = OLDEST;
            break;
            case 2:
                this.sortOrder = SortOrder.NEWEST;
            break;
            default:
                this.sortOrder = SortOrder.NONE;
            break;
        }
    }

    public String getSortOrderQueryParam(){
        switch (this.sortOrder){
            case OLDEST:
                return "oldest";
            case NEWEST:
                return "newest";
        }
        return "";
    }

    public String getNewsDeskQueryParam() {
        ArrayList<String> news_desks = new ArrayList<>();

        if(this.arts){
            news_desks.add("\"arts\"");
        }

        if(this.fashion){
            news_desks.add("\"fashion\"");
        }

        if(this.sports){
            news_desks.add("\"sports\"");
        }

        return String.format("news_desk:(%s)", TextUtils.join(" ", news_desks));
    }
}
