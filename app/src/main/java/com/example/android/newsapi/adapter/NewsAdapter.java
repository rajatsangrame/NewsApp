package com.example.android.newsapi.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.newsapi.R;
import com.example.android.newsapi.databinding.NewsItemBinding;
import com.example.android.newsapi.model.Article;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> implements Filterable {
    private static final String TAG = "NewsAdapter";
    private final NewsAdapterListener mListener;
    private List<Article> mArticles;
    private List<Article> mArticlesListFiltered;

    public NewsAdapter(List<Article> articles, NewsAdapterListener listener) {
        mArticles = articles;
        mArticlesListFiltered = articles;
        mListener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        NewsItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.news_item, parent, false);
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i) {

        newsViewHolder.binding.setNews(mArticlesListFiltered.get(i));
        newsViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mArticlesListFiltered == null ? 0 : mArticlesListFiltered.size();
    }

    public void setArticles(List<Article> articles) {
        if (articles != null) {
            mArticles = articles;
            mArticlesListFiltered = articles;
            notifyDataSetChanged();
        }
    }

    public interface NewsAdapterListener {
        void onNewsItemClicked(Article article, View view);

        void onItemOptionsClicked(Article article);
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final NewsItemBinding binding;

        public NewsViewHolder(final NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.ivOptions.setOnClickListener(this);
            this.binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int index = this.getAdapterPosition();
            if (view instanceof ImageView) {
                mListener.onItemOptionsClicked(mArticlesListFiltered.get(index));
            } else {
                mListener.onNewsItemClicked(mArticlesListFiltered.get(index), binding.tvNewsTitle);
            }
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    mArticlesListFiltered = mArticles;
                } else {
                    List<Article> filteredList = new ArrayList<>();
                    if (mArticles == null || mArticles.isEmpty()) {
                        return new FilterResults();
                    }
                    for (Article row : mArticles) {
                        try {

                            if (row.getTitle().toLowerCase().contains(charString) ||
                                    row.getSource().getName().contains(charString) ||
                                    row.getAuthor().contains(charSequence) ||
                                    row.getDescription().contains(charSequence) ||
                                    row.getUrl().contains(charSequence)) {
                                filteredList.add(row);
                            }

                        } catch (Exception e) {
                            Log.i(TAG, "performFiltering: " + e.getMessage());
                        }
                    }

                    mArticlesListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mArticlesListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                mArticlesListFiltered = (List<Article>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
