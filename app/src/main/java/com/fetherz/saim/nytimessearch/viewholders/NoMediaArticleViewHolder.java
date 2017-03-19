package com.fetherz.saim.nytimessearch.viewholders;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fetherz.saim.nytimessearch.R;
import com.fetherz.saim.nytimessearch.models.nytimes.articles.Doc;
import com.fetherz.saim.nytimessearch.utils.LogUtil;

import java.util.List;

/**
 * Created by sm032858 on 3/19/17.
 */

public class NoMediaArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView tvNoMediaArticleTitle;

    TextView tvNoMediaArticleMoniker;

    TextView tvNoMediaArticleSnippet;

    private List<Doc> articles;

    private Context context;

    public NoMediaArticleViewHolder(View v, List<Doc> articles) {
        super(v);
        context = v.getContext();

        tvNoMediaArticleTitle = (TextView) v.findViewById(R.id.tvNoMediaArticleTitle);
        tvNoMediaArticleMoniker = (TextView) v.findViewById(R.id.tvNoMediaArticleMoniker);
        tvNoMediaArticleSnippet = (TextView) v.findViewById(R.id.tvNoMediaArticleSnippet);

        this.articles = articles;

        v.setOnClickListener(this);
    }

    public TextView getTvNoMediaArticleTitle() {
        return tvNoMediaArticleTitle;
    }

    public void setTvNoMediaArticleTitle(TextView tvNoMediaArticleTitle) {
        this.tvNoMediaArticleTitle = tvNoMediaArticleTitle;
    }

    public TextView getTvNoMediaArticleMoniker() {
        return tvNoMediaArticleMoniker;
    }

    public void setTvNoMediaArticleMoniker(TextView tvNoMediaArticleMoniker) {
        this.tvNoMediaArticleMoniker = tvNoMediaArticleMoniker;
    }

    public TextView getTvNoMediaArticleSnippet() {
        return tvNoMediaArticleSnippet;
    }

    public void setTvNoMediaArticleSnippet(TextView tvNoMediaArticleSnippet) {
        this.tvNoMediaArticleSnippet = tvNoMediaArticleSnippet;
    }

    @Override
    public void onClick(View v) {
        int position = getLayoutPosition();
        Doc article = articles.get(position);

        Uri uri = Uri.parse(article.getWebUrl());

        // create an intent builder
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_social);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, uri);

        int requestCode = 100;

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        intentBuilder.setActionButton(bitmap, "Share Link", pendingIntent, true);

        // Begin customizing
        // set toolbar colors
        intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.theme_primary_light));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.theme_primary_dark));

        // set start and exit animations
        intentBuilder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left);
        intentBuilder.setExitAnimations(context, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        // build custom tabs intent
        CustomTabsIntent customTabsIntent = intentBuilder.build();


        LogUtil.logD("ARTICLE_ACCESS", "Article clicked: " + article);

        // launch the url
        customTabsIntent.launchUrl(context, uri);
    }
}