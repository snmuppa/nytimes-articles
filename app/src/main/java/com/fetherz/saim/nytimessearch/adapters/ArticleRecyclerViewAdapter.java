package com.fetherz.saim.nytimessearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fetherz.saim.nytimessearch.R;
import com.fetherz.saim.nytimessearch.images.ImageLoaderImpl;
import com.fetherz.saim.nytimessearch.models.nytimes.articles.Doc;
import com.fetherz.saim.nytimessearch.viewholders.MultiMediaArticleViewHolder;
import com.fetherz.saim.nytimessearch.viewholders.NoMediaArticleViewHolder;

import java.util.List;

/**
 * Created by sm032858 on 3/18/17.
 */
public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private List<Doc> articles;

    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ArticleRecyclerViewAdapter(List<Doc> articles) {
        this.articles = articles;
    }

    @Override
    public int getItemViewType(int position) {
        Doc article = articles.get(position);
        return article.getViewType();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.articles.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        context = viewGroup.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case Doc.ARTICLE_WITH_MEDIA:
                View mediaArticleView = inflater.inflate(R.layout.multi_media_article, viewGroup, false);
                viewHolder = new MultiMediaArticleViewHolder(mediaArticleView, articles);
                break;
            default:
                View noMediaArticleView = inflater.inflate(R.layout.no_media_article, viewGroup, false);
                viewHolder = new NoMediaArticleViewHolder(noMediaArticleView, articles);
                break;
        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case Doc.ARTICLE_WITH_MEDIA:
                MultiMediaArticleViewHolder mediaArticleViewHolder = (MultiMediaArticleViewHolder) viewHolder;
                bindMediaViewHolder(mediaArticleViewHolder, position);
                break;
            default:
                NoMediaArticleViewHolder noMediaArticleViewHolder = (NoMediaArticleViewHolder) viewHolder;
                bindNoMediaViewHolder(noMediaArticleViewHolder, position);
                break;
        }
    }

    private void bindNoMediaViewHolder(NoMediaArticleViewHolder noMediaArticleViewHolder, int position) {
        Doc article  = articles.get(position);
        if (article != null) {
            noMediaArticleViewHolder.getTvNoMediaArticleTitle().setText(article.getHeadline().getMain());
            noMediaArticleViewHolder.getTvNoMediaArticleMoniker().setText(article.getNewsDesk());
            noMediaArticleViewHolder.getTvNoMediaArticleMoniker().setBackgroundResource(article.getMonikerBackground());
            noMediaArticleViewHolder.getTvNoMediaArticleSnippet().setText(article.getSnippet());
        }
    }

    private void bindMediaViewHolder(MultiMediaArticleViewHolder mediaArticleViewHolder, int position) {
        Doc article  = articles.get(position);
        if (article != null) {
            ImageLoaderImpl imageLoader = new ImageLoaderImpl();
            imageLoader.loadImage(article.getMultiMediaThumbnailImage(), mediaArticleViewHolder.getIvArticle());
            mediaArticleViewHolder.getTvArticleTitle().setText(article.getHeadline().getMain());
            mediaArticleViewHolder.getTvArticleMoniker().setText(article.getNewsDesk());
            mediaArticleViewHolder.getTvArticleMoniker().setBackgroundResource(article.getMonikerBackground());
            mediaArticleViewHolder.getTvArticleSnippet().setText(article.getSnippet());
        }
    }


    // Clean all elements of the recycler
    public void clear() {
        articles.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Doc> list) {
        articles.addAll(list);
        notifyDataSetChanged();
    }
}
