package com.fetherz.saim.nytimessearch.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.fetherz.saim.nytimessearch.R;
import com.fetherz.saim.nytimessearch.fragments.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sm032858 on 3/17/17.
 */

public class ArticleSearchSettingsDialog extends DialogFragment {
    @BindView(R.id.spnSortOrder)
    Spinner spnSortOrder;

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;

    @BindView(R.id.etDate)
    EditText etBeginDate;

    @BindView(R.id.cbArts)
    CheckBox artsCheckbox;

    @BindView(R.id.cbFashion)
    CheckBox cbFashion;

    @BindView(R.id.cbSports)
    CheckBox cbSports;

    public ArticleSearchSettingsDialog(){}

    public static ArticleSearchSettingsDialog newInstance(){
        ArticleSearchSettingsDialog articleSearchSettingsDialog = new ArticleSearchSettingsDialog();
        return articleSearchSettingsDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.content_article_search_settings, container);
        ButterKnife.bind(this, view);

        setDate(new Date());

        return view;
    }

    @OnClick(R.id.etDate)
    public void showDatePicker(View view){
        android.support.v4.app.DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getActivity().getSupportFragmentManager(), "settings_date_picker");
    }

    @OnClick(R.id.fabSave)
    public void onSave(){

        dismiss();
    }

    public void setDate(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        etBeginDate.setText( sdf.format(time));
    }
}
