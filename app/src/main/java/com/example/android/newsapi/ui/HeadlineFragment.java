package com.example.android.newsapi.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.newsapi.R;
import com.example.android.newsapi.adapter.NewsAdapter;
import com.example.android.newsapi.databinding.FragmentHeadlineBinding;
import com.example.android.newsapi.network.NewsApi;
import com.google.android.material.tabs.TabLayout;


public class HeadlineFragment extends Fragment {

    private FragmentHeadlineBinding mBinding;
    private NewsFragment.UiChangeListener[] uiChangeListeners;
    private final String[] CATEGORIES = {
            NewsApi.Category.general.name(),
            NewsApi.Category.business.name(),
            NewsApi.Category.sports.name(),
            NewsApi.Category.health.name(),
            NewsApi.Category.entertainment.name(),
            NewsApi.Category.technology.name(),
            NewsApi.Category.science.name(),
    };
    private final int[] CATEGORIES_ICON = {
            R.drawable.ic_headlines,
            R.drawable.nav_business,
            R.drawable.nav_sports,
            R.drawable.nav_health,
            R.drawable.nav_entertainment,
            R.drawable.nav_tech,
            R.drawable.nav_science,

    };

    public NewsFragment.UiChangeListener[] getUiChangeListener() {
        return uiChangeListeners;
    }

    public HeadlineFragment() {
    }

    public static HeadlineFragment newInstance() {
        return new HeadlineFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.option_search);
        if (item != null)
            item.setVisible(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_headline, container, false);

        if (getActivity() != null) {
            ViewPagerAdapter viewPager = new ViewPagerAdapter(getChildFragmentManager(), CATEGORIES);
            mBinding.viewpagerHeadlines.setAdapter(viewPager);
            mBinding.tablayoutHeadlines.setupWithViewPager(mBinding.viewpagerHeadlines);
            setupTabIcons();
            uiChangeListeners = viewPager.getUiChangeListener();
        }
        ViewCompat.setElevation(mBinding.tablayoutHeadlines, getResources()
                .getDimension(R.dimen.tab_layout_elevation));

        return mBinding.getRoot();
    }

    private void setupTabIcons() {
        TabLayout.Tab tab;
        for (int i = 0; i < CATEGORIES.length; i++) {
            tab = mBinding.tablayoutHeadlines.getTabAt(i);
            if (tab != null) {
                tab.setIcon(CATEGORIES_ICON[i]).setText(CATEGORIES[i]);
            }
        }
    }



}
