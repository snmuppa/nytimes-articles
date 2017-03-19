package com.fetherz.saim.nytimessearch.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fetherz.saim.nytimessearch.R;

/**
 * Created by sm032858 on 3/18/17.
 */

public class MultiMediaArticleViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivNonPopularMovie;
    private TextView tvArticleTitle;
    private TextView tvArticleMoniker;
    private TextView tvArticleSnippet;

    public MultiMediaArticleViewHolder(View v) {
        super(v);
        ivNonPopularMovie = (ImageView) v.findViewById(R.id.ivArticle);
        tvArticleTitle = (TextView) v.findViewById(R.id.tvArticleTitle);
        tvArticleMoniker = (TextView) v.findViewById(R.id.tvArticleMoniker);
        tvArticleSnippet = (TextView) v.findViewById(R.id.tvArticleSnippet);
    }

    public ImageView getIvNonPopularMovie() {
        return ivNonPopularMovie;
    }

    public void setIvNonPopularMovie(ImageView ivNonPopularMovie) {
        this.ivNonPopularMovie = ivNonPopularMovie;
    }

    public TextView getTvArticleTitle() {
        return tvArticleTitle;
    }

    public void setTvArticleTitle(TextView tvArticleTitle) {
        this.tvArticleTitle = tvArticleTitle;
    }

    public TextView getTvArticleMoniker() {
        return tvArticleMoniker;
    }

    public void setTvArticleMoniker(TextView tvArticleMoniker) {
        this.tvArticleMoniker = tvArticleMoniker;
    }

    public TextView getTvArticleSnippet() {
        return tvArticleSnippet;
    }

    public void setTvArticleSnippet(TextView tvArticleSnippet) {
        this.tvArticleSnippet = tvArticleSnippet;
    }

}