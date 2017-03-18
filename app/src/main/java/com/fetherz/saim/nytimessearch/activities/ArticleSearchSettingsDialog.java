package com.fetherz.saim.nytimessearch.activities;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.fetherz.saim.nytimessearch.R;
import com.fetherz.saim.nytimessearch.fragments.DatePickerFragment;
import com.fetherz.saim.nytimessearch.models.search.settings.FilterSelection;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sm032858 on 3/17/17.
 */
public class ArticleSearchSettingsDialog extends DialogFragment {

    /**
     * Define the events that the fragment will use to communicate
     */
    public interface OnItemSelectedListener {
        // This can be any number of events to be sent to the activity
        public void onSearchSettingsSelected(FilterSelection currentFilters);
    }

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

    /**
     *
     */
    public ArticleSearchSettingsDialog(){}

    /**
     *
     * @return
     */
    public static ArticleSearchSettingsDialog newInstance(){
        ArticleSearchSettingsDialog articleSearchSettingsDialog = new ArticleSearchSettingsDialog();
        return articleSearchSettingsDialog;
    }


    /**
     * Define the listener of the interface type
     * listener will the activity instance containing fragment
     */
    private OnItemSelectedListener listener;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstance
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = getActivity().getLayoutInflater().inflate(R.layout.content_article_search_settings, container);
        ButterKnife.bind(this, view);

        setDate(new Date());

        return view;
    }

    /**
     * Store the listener (activity) that will have events fired once the fragment is attached
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    /**
     *
     * @param view
     */
    @OnClick(R.id.etDate)
    public void showDatePicker(View view){
        android.support.v4.app.DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getActivity().getSupportFragmentManager(), "settings_date_picker");
    }

    /**
     *
     */
    @OnClick(R.id.fabSave)
    public void onSave(){

        //TODO: set the FilterSelection with updated selection items

        listener.onSearchSettingsSelected(FilterSelection.getInstance()); //pass the updated instance of the FilterSelection class
        dismiss();
    }

    /**
     *
     * @param time
     */
    public void setDate(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        etBeginDate.setText( sdf.format(time));
    }

    /**
     *
     */
    @Override
    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.90), (int) (size.y * 0.80));
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }
}
