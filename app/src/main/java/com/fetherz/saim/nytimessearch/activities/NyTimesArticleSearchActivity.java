package com.fetherz.saim.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.fetherz.saim.nytimessearch.R;
import com.fetherz.saim.nytimessearch.models.search.settings.FilterSelection;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class NyTimesArticleSearchActivity extends AppCompatActivity implements ArticleSearchSettingsDialog.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.clSearchActivity)
    CoordinatorLayout clSearchActivity;

    ArticleSearchSettingsDialog articleSearchSettingsDialog;
    Calendar calendar;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ny_times_article_search);
        ButterKnife.bind(NyTimesArticleSearchActivity.this);
        setSupportActionBar(toolbar);
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ny_times_article_search, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            openSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openSettings() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        articleSearchSettingsDialog = ArticleSearchSettingsDialog.newInstance();
        articleSearchSettingsDialog.show(fragmentManager, "search_settings");
    }

    /**
     *
     * @param view
     * @param year
     * @param month
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        articleSearchSettingsDialog.setDate(calendar.getTime());
    }

    /**
     * Now we can define the action to take in the activity when the fragment event fires
     * This is implementing the `OnItemSelectedListener` interface methods
     * @param currentFilters
     */
    @Override
    public void onSearchSettingsSelected(FilterSelection currentFilters) {
        Snackbar.make(clSearchActivity, currentFilters.getBeginDateMMddyyyy(), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }
}
