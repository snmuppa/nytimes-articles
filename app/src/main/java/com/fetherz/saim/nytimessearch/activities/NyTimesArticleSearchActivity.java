package com.fetherz.saim.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.fetherz.saim.nytimessearch.models.nytimes.articles.Article;
import com.fetherz.saim.nytimessearch.models.nytimes.articles.Doc;
import com.fetherz.saim.nytimessearch.models.search.settings.FilterSelection;
import com.fetherz.saim.nytimessearch.services.ArticleService;
import com.fetherz.saim.nytimessearch.utils.LogUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class NyTimesArticleSearchActivity extends AppCompatActivity implements ArticleSearchSettingsDialog.OnItemSelectedListener, DatePickerDialog.OnDateSetListener,
        ArticleService.ArticleListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.clSearchActivity)
    CoordinatorLayout clSearchActivity;

    @BindView(R.id.rvArticles)
    RecyclerView rvArticles;

    ArticleRecyclerViewAdapter articleRecyclerViewAdapter;

    ArticleSearchSettingsDialog articleSearchSettingsDialog;
    Calendar calendar;
    ArticleService articleService;
    Article article;

    private static final String API_KEY_VALUE = "ca395a3acdfb48cb9ccfd66c7171f522";
    private static final int START_PAGE = 0;
    List<Doc> articles;

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

                // Initialize movies
                articles = new ArrayList<>();

                // Create adapter passing in the initial movie data
                articleRecyclerViewAdapter = new ArticleRecyclerViewAdapter(articles);

                // Attach the adapter to the recyclerview to populate items
                rvArticles.setAdapter(articleRecyclerViewAdapter);

                // First param is number of columns and second param is orientation i.e Vertical or Horizontal
                StaggeredGridLayoutManager gridLayoutManager =
                        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

                // Set layout manager to position the items
                rvArticles.setLayoutManager(gridLayoutManager);

                // perform query here
                fetchArticles(searchQuery, START_PAGE);
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

    private void fetchArticles(String searchQuery, int page) {
        articleService.requestArticles(API_KEY_VALUE, searchQuery, "news_desk:(\"Sports\")", "20170101", "newest", page);
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

    /**
     *
     * @param article
     */
    @Override
    public void onArticleLoaded(Article article) {
        this.article = article;

        if(this.article != null && this.article.getResponse() != null && this.article.getResponse().getDocs() != null)
        {
            articles.addAll(0, this.article.getResponse().getDocs());
            articleRecyclerViewAdapter.notifyItemRangeInserted(0, this.article.getResponse().getDocs().size());
        }
    }

    /**
     *
     * @param message
     */
    @Override
    public void onArticleLoadFailed(String message) {
        LogUtil.logD("Failed", message);
    }

    /**
     *
     */
    @Override
    public void onDestroy() {
        articleService.removeListener(this);
        super.onDestroy();
    }
}
