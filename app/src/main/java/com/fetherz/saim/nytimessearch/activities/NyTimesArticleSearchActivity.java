package com.fetherz.saim.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.fetherz.saim.nytimessearch.R;
import com.fetherz.saim.nytimessearch.adapters.ArticleRecyclerViewAdapter;
import com.fetherz.saim.nytimessearch.application.ArticleApplication;
import com.fetherz.saim.nytimessearch.eventlisteners.EndlessRecyclerViewScrollListener;
import com.fetherz.saim.nytimessearch.models.nytimes.articles.Article;
import com.fetherz.saim.nytimessearch.models.nytimes.articles.Doc;
import com.fetherz.saim.nytimessearch.models.search.settings.FilterSelection;
import com.fetherz.saim.nytimessearch.services.ArticleService;
import com.fetherz.saim.nytimessearch.utils.LogUtil;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class NyTimesArticleSearchActivity extends BaseActivity
        implements ArticleSearchSettingsDialog.OnItemSelectedListener,
        DatePickerDialog.OnDateSetListener,
        ArticleService.ArticleListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.clSearchActivity)
    CoordinatorLayout clSearchActivity;

    @BindView(R.id.rvArticles)
    RecyclerView rvArticles;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    ArticleRecyclerViewAdapter articleRecyclerViewAdapter;
    ArticleSearchSettingsDialog articleSearchSettingsDialog;
    // Store a member variable for the listener
    EndlessRecyclerViewScrollListener scrollListener;

    Calendar calendar;
    ArticleService articleService;
    Article article;

    static final List<String> API_KEYS = new ArrayList<>();

    static { //hack: as the API throttles heavily, and occasionally blocks quick contigous requests, this eliminates that issue by using multiple authorities
        API_KEYS.add("ca395a3acdfb48cb9ccfd66c7171f522");
        API_KEYS.add("6c0ec4c8f91b4be69a2a454bac24a9e2");
        API_KEYS.add("1cf6be05020f46dea300c7e1f9dda6a8");
    }

    static final int START_PAGE = 0;
    List<Doc> articles;

    String currentSearchQuery = "ny times";

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
        setRecyclerView();
        setSwipeRefreshContainer();
        articleService = ((ArticleApplication) getApplicationContext()).getArticleService();
        articleService.addListener(this);
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
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchQuery) {
                //cache the current query
                currentSearchQuery = searchQuery;

                fetchArticlesFresh();

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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
        fetchArticlesFresh();//freshly fetch on settings update
    }

    /**
     *
     * @param article
     */
    @Override
    public void onArticleLoaded(Article article) {
        this.article = article;

        if(this.article != null && this.article.getResponse() != null && this.article.getResponse().getDocs() != null)
        {
            int currSize = articleRecyclerViewAdapter.getItemCount();
            articles.addAll(this.article.getResponse().getDocs());
            articleRecyclerViewAdapter.notifyItemRangeInserted(currSize, this.article.getResponse().getDocs().size() - 1);
        }

        // Now we call setRefreshing(false) to signal refresh has finished
        swipeContainer.setRefreshing(false);
    }

    /**
     *
     * @param message
     */
    @Override
    public void onArticleLoadFailed(String message) {
        LogUtil.logE("Failed", message);
    }

    /**
     *
     */
    @Override
    public void onDestroy() {
        articleService.removeListener(this);
        super.onDestroy();
    }

    private void openSettings() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        articleSearchSettingsDialog = ArticleSearchSettingsDialog.newInstance();
        articleSearchSettingsDialog.show(fragmentManager, "search_settings");
    }

    /**
     *
     */
    private void fetchArticlesFresh() {
        //reset the endless scroller before re-fetching based on new query
        resetEndlessScroller();

        // perform query here
        fetchArticles(START_PAGE);
    }

    /**
     *
     * @param page
     */
    private void fetchArticles(int page) {

        if(!isOnline()){ //if the failure is due to no internet then
            Snackbar.make(clSearchActivity, "No internet.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            return;
        }

        FilterSelection filterSelection = FilterSelection.getInstance();//get the settings instance
        try {

            String apiKey = API_KEYS.get(page % 3);

            articleService.requestArticles(apiKey,
                    currentSearchQuery,
                    filterSelection.getNewsDeskQueryParam(),
                    filterSelection.getBeginDateQueryParam(),
                    filterSelection.getSortOrderQueryParam(),
                    page);
        } catch (IndexOutOfBoundsException e){
            LogUtil.logE("Invalid_Key_Request", e.getMessage());
        }
        catch (InvalidObjectException e) {
            LogUtil.logE("Invalid_Begin_Date", e.getMessage());
        }
    }

    /**
     *
     */
    private void setSwipeRefreshContainer() {
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(() -> {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            swipeRefreshArticleData();
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    /**
     *
     */
    private void swipeRefreshArticleData() {
        //reset the articles before refresh
        fetchArticlesFresh();
    }

    /**
     *
     */
    private void setRecyclerView() {
        // Initialize articles
        articles = new ArrayList<>();

        // Create adapter passing in the initial article data
        articleRecyclerViewAdapter = new ArticleRecyclerViewAdapter(articles);

        // Attach the adapter to the recyclerview to populate items
        rvArticles.setAdapter(articleRecyclerViewAdapter);

        // First param is number of columns and second param is orientation i.e Vertical or Horizontal
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        // Set layout manager to position the items
        rvArticles.setLayoutManager(gridLayoutManager);

        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                fetchArticles(page); //ny times API page numbers start from 0
            }
        };

        // Adds the scroll listener to RecyclerView
        rvArticles.addOnScrollListener(scrollListener);
    }

    /**
     *
     * @return
     */
    private boolean isOnline(){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (InterruptedException | IOException e) {
            LogUtil.logE("Failed_Connection", e.getMessage());
        }
        return false;
    }

    /**
     *
     */
    private void resetEndlessScroller() {
        // 1. First, clear the array of data
        articles.clear();
        // 2. Notify the adapter of the update
        articleRecyclerViewAdapter.notifyDataSetChanged(); // or notifyItemRangeRemoved
        // 3. Reset endless scroll listener when performing a new search
        scrollListener.resetState();
    }
}
