package com.fetherz.saim.nytimessearch.services;

import android.text.TextUtils;

import com.fetherz.saim.nytimessearch.models.nytimes.articles.Article;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sm032858 on 3/18/17.
 */
public class ArticleService {

    private ArrayList<ArticleListener> listeners = new ArrayList<>();

    final NyTimesServiceInterface nyTimesServiceInterface = NyTimesServiceInterface.retrofit.create(NyTimesServiceInterface.class);

    private Article article = new Article();

    /**
     *
     * @param listener
     */
    public void addListener(ArticleListener listener) {
        listeners.add(listener);
    }

    /**
     *
     * @param listener
     */
    public void removeListener(ArticleListener listener) {
        listeners.remove(listener);
    }

    /**
     *
     * @param apiKey
     * @param searchQuery
     * @param filterQuery
     * @param beginDate
     * @param sortOrder
     * @param page
     */
    public void requestArticles(String apiKey, String searchQuery, String filterQuery,
                                    String beginDate, String sortOrder, int page) {
        if (TextUtils.isEmpty(apiKey) || TextUtils.isEmpty(searchQuery)) {
            for (ArticleListener listener : listeners) {
                listener.onArticleLoadFailed("Invalid arguments");
            }
            return;
        }
        final Call<Article> call = nyTimesServiceInterface.searchArticles(apiKey, searchQuery, filterQuery, beginDate, sortOrder, page);
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if (response.isSuccessful()) {
                    article = response.body();
                    for (ArticleListener listener : listeners) {
                        listener.onArticleLoaded(article);
                    }
                } else {
                    for (ArticleListener listener : listeners) {
                        listener.onArticleLoadFailed(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                for (ArticleListener listener : listeners) {
                    listener.onArticleLoadFailed(t.getMessage());
                }
            }

        });
    }

    /**
     *
     */
    public void requestCachedArticle() {
        for (ArticleListener listener : listeners) {
            listener.onArticleLoaded(article);
        }

    }

    /**
     *
     */
    public interface ArticleListener {
        void onArticleLoaded(Article article);
        void onArticleLoadFailed(String message);
    }
}
