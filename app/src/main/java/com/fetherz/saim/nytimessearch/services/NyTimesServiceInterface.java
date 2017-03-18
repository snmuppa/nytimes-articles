package com.fetherz.saim.nytimessearch.services;

import com.fetherz.saim.nytimessearch.models.nytimes.articles.Article;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sm032858 on 3/18/17.
 */

public interface NyTimesServiceInterface {

    static final String NY_TIMES_API_BASE_URI = "http://api.nytimes.com/svc/";

    @GET("search/v2/articlesearch.json")
    Call<Article> searchArticles(@Query("api_key") String apiKey, @Query("q") String searchQuery, @Query("fq") String filterQuery,
                                 @Query("begin_date") String beginDate, @Query("sort") String sortOrder, @Query("page") int page);

    OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(NY_TIMES_API_BASE_URI)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
            .build();
}
