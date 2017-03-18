package com.fetherz.saim.nytimessearch.application;

import android.app.Application;
import android.os.StrictMode;

import com.fetherz.saim.nytimessearch.services.ArticleService;

/**
 * Created by sm032858 on 3/18/17.
 */

public class ArticleApplication extends Application {
    private ArticleService articleService;

    @Override
    public void onCreate() {
        super.onCreate();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectAll()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());


        articleService = new ArticleService();

    }

    public ArticleService getArticleService() {
        return articleService;
    }
}
