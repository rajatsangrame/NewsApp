package com.example.android.newsapi.ui;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.android.newsapi.network.NewsApi;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "ViewPagerAdapter";
    private final NewsFragment[] newsFragments;
    private NewsFragment.UiChangeListener[] uiChangeListeners;

    public ViewPagerAdapter(FragmentManager fm, String[] categories) {
        super(fm);
        newsFragments = new NewsFragment[categories.length];
        uiChangeListeners = new NewsFragment.UiChangeListener[categories.length];
        for (int i = 0; i < categories.length; i++) {
            newsFragments[i] = NewsFragment.newInstance(NewsApi.Category.valueOf(categories[i]));
            uiChangeListeners[i] = newsFragments[i].getUiChangeListener();
        }
    }

    public NewsFragment.UiChangeListener[] getUiChangeListener() {
        return uiChangeListeners;
    }

    @Override
    public Fragment getItem(int i) {

        return newsFragments[i];
    }

    @Override
    public int getCount() {
        return newsFragments == null ? 0 : newsFragments.length;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


}
