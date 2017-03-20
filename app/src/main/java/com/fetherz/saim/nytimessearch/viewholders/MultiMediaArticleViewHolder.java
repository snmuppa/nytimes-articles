package com.fetherz.saim.nytimessearch.viewholders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fetherz.saim.nytimessearch.R;
import com.fetherz.saim.nytimessearch.chromecustomtab.CustomTabActivityHelper;
import com.fetherz.saim.nytimessearch.models.nytimes.articles.Doc;
import com.fetherz.saim.nytimessearch.utils.LogUtil;

import java.util.List;

/**
 * Created by sm032858 on 3/18/17.
 */
public class MultiMediaArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView ivArticle;

    TextView tvArticleTitle;

    TextView tvArticleMoniker;

    TextView tvArticleSnippet;

    private List<Doc> articles;

    private Context context;

    public MultiMediaArticleViewHolder(View v, List<Doc> articles) {
        super(v);
        context = v.getContext();

        ivArticle = (ImageView) v.findViewById(R.id.ivArticle);
        tvArticleTitle = (TextView) v.findViewById(R.id.tvArticleTitle);
        tvArticleMoniker = (TextView) v.findViewById(R.id.tvArticleMoniker);
        tvArticleSnippet = (TextView) v.findViewById(R.id.tvArticleSnippet);

        this.articles = articles;

        v.setOnClickListener(this);
    }

    public ImageView getIvArticle() {
        return ivArticle;
    }

    public TextView getTvArticleTitle() {
        return tvArticleTitle;
    }

    public TextView getTvArticleMoniker() {
        return tvArticleMoniker;
    }

    public TextView getTvArticleSnippet() {
        return tvArticleSnippet;
    }

    @Override
    public void onClick(View v) {
        int position = getLayoutPosition();
        Doc article = articles.get(position);

        Uri uri = Uri.parse(article.getWebUrl());

        // create an intent builder
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();

        Bitmap bitmap = BitmapFactory.decodeResource(v.getContext().getResources(), R.mipmap.ic_launcher_social);

        // Begin customizing
        // set toolbar colors
        intentBuilder.setToolbarColor(ContextCompat.getColor(v.getContext(), R.color.theme_primary_light));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(v.getContext(), R.color.theme_primary_dark));

        intentBuilder.setShowTitle(true);
        intentBuilder.addDefaultShareMenuItem();
        intentBuilder.enableUrlBarHiding();

        // build custom tabs intent
        CustomTabsIntent customTabsIntent = intentBuilder.build();

        if (v.getContext() instanceof Activity) {
            CustomTabActivityHelper.openCustomTab((Activity) v.getContext(), customTabsIntent, uri,
                    (activity, uri1) -> {
                        Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
                        activity.startActivity(intent1);
                    });
        }
        else{
            v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }

        LogUtil.logD("ARTICLE_ACCESS", "Article clicked: " + article);
    }
}