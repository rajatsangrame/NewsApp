package com.example.android.newsapi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.newsapi.BR;
import com.example.android.newsapi.R;
import com.example.android.newsapi.databinding.SourceItemBinding;
import com.example.android.newsapi.model.Source;
import com.example.android.newsapi.ui.NewsGlideModule;

import java.util.List;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.SourceViewHolder> {
    private final SourceAdapterListener listener;
    private List<Source> sources;
    private int mExpandedPosition = -1;

    public SourceAdapter(List<Source> sources, SourceAdapterListener listener) {
        this.sources = sources;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SourceItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.source_item, parent, false);
        return new SourceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final SourceViewHolder sourceViewHolder, final int i) {
        sourceViewHolder.binding.setSource(sources.get(i));
        final int position = sourceViewHolder.getAdapterPosition();
        final boolean isExpanded = position == mExpandedPosition;
        sourceViewHolder.binding.tvSourceDesc.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        sourceViewHolder.binding.btnOpen.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        sourceViewHolder.binding.getRoot().setActivated(isExpanded);
        sourceViewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : position;
                notifyDataSetChanged();
            }
        });
        sourceViewHolder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return sources == null ? 0 : sources.size();
    }

    public void setSources(List<Source> sources) {
        if (sources != null) {
            this.sources = sources;
            notifyDataSetChanged();
        }
    }


    public interface SourceAdapterListener {
        void onSourceButtonClicked(Source source);
    }

    class SourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final SourceItemBinding binding;

        SourceViewHolder(final SourceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.btnOpen.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onSourceButtonClicked(this.binding.getSource());
        }
    }
}
