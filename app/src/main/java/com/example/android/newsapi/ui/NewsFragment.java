package com.example.android.newsapi.ui;

import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.android.newsapi.R;
import com.example.android.newsapi.adapter.NewsAdapter;
import com.example.android.newsapi.databinding.NewsFragmentBinding;
import com.example.android.newsapi.model.Article;
import com.example.android.newsapi.network.NewsApi;
import com.example.android.newsapi.network.Specification;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment implements NewsAdapter.NewsAdapterListener {


    private static final String TAG = "NewsFragment";

    private NewsFragmentBinding mBinding;
    private NewsApi.Category mCategory;

    public UiChangeListener mUiChangeListener = new UiChangeListener() {
        @Override
        public void onSearchResult(String query) {
            mNewsAdapter.getFilter().filter(query);
        }
    };

    private final NewsAdapter mNewsAdapter = new NewsAdapter(null, this);

    public static NewsFragment newInstance(NewsApi.Category category) {
        NewsFragment fragment = new NewsFragment();
        if (category == null) {
            return fragment;
        }
        Bundle args = new Bundle();
        args.putString(NewsApi.Param.ITEM_CATEGORY, category.name());
        fragment.setArguments(args);
        return fragment;
    }

    public UiChangeListener getUiChangeListener() {
        return mUiChangeListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.news_fragment, container, false);
        RecyclerView recyclerView = mBinding.rvNewsPosts;
        recyclerView.setAdapter(mNewsAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NewsViewModel viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        Specification specs = new Specification();
        specs.setCategory(mCategory);
        viewModel.getNewsHeadlines(specs).observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null) {
                    mNewsAdapter.setArticles(articles);
                    restoreRecyclerViewState();
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mCategory = NewsApi.Category
                    .valueOf(getArguments().getString(NewsApi.Param.ITEM_CATEGORY));
        } else {
            mCategory = NewsApi.Category.general;
        }
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.option_search);
        if (item != null)
            item.setVisible(true);
    }

    private void restoreRecyclerViewState() {
        if (mBinding.rvNewsPosts.getLayoutManager() != null) {
            //mBinding.rvNewsPosts.getLayoutManager().onRestoreInstanceState(listState);
        }
    }


    @Override
    public void onNewsItemClicked(final Article article, View view) {

        OptionsBottomSheet bottomSheet = OptionsBottomSheet.getInstance(article.getTitle(),
                article.getUrl(), article.getId(), false);
        if (getActivity() != null) {
            bottomSheet.show(getActivity().getSupportFragmentManager(), bottomSheet.getTag());
        }

        //region Show Pop UP Menu
        /*
        final PopupMenu popup = new PopupMenu(view.getContext(), view);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.article, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_share:
                        String shareText = article.getTitle() + "\n" + article.getUrl();
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, shareText);
                        intent.setType("text/plain");
                        popup.dismiss();
                        startActivity(intent);
                        break;
                    case R.id.menu_save:
                        Toast.makeText(getContext(), "This feature is coming soon!", Toast.LENGTH_SHORT).show();
                        popup.dismiss();
                        break;
                    case R.id.menu_open:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        popup.show();
         */
        //endregion
    }

    @Override
    public void onItemOptionsClicked(Article article) {
    }

    public interface UiChangeListener {
        void onSearchResult(String query);
    }
}
