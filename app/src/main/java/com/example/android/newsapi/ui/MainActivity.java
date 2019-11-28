package com.example.android.newsapi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.android.newsapi.R;
import com.example.android.newsapi.databinding.ActivityMainBinding;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private final FragmentManager mFragmentManager = getSupportFragmentManager();
    private ActivityMainBinding mBinding;
    private NewsFragment.UiChangeListener[] mUiChangeListener;
    private HeadlineFragment mHeadlineFragment;
    private SourceFragment mSourceFragment;


    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {

                case R.id.navigation_news:
                    mFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, mHeadlineFragment)
                            .commit();
                    mUiChangeListener = mHeadlineFragment.getUiChangeListener();
                    return true;

                case R.id.navigation_saved:
                    /*
                    if (mNewsFragment == null) {
                        mNewsFragment = NewsFragment.newInstance(null);
                    }*/
                    mFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, new Fragment())
                            .commit();
                    Toast.makeText(MainActivity.this, "This feature is coming soon!", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.navigation_sources:
                    if (mSourceFragment == null) {
                        mSourceFragment = SourceFragment.newInstance();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, mSourceFragment)
                            .commit();

                    return true;
            }
            return false;
        }
    };

    private void setupToolbar() {

        setSupportActionBar(mBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.app_name));

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setupToolbar();

        if (savedInstanceState == null) {
            // Add a default fragment
            mHeadlineFragment = HeadlineFragment.newInstance();
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, mHeadlineFragment)
                    .commit();
            mUiChangeListener = mHeadlineFragment.getUiChangeListener();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        mBinding.toolbar.inflateMenu(R.menu.option_bar);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        assert searchManager != null;
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.option_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                if (query.isEmpty()) {
                    return false;
                }

                if (mHeadlineFragment.getUiChangeListener() == null) {
                    return false;
                }

                mUiChangeListener = mHeadlineFragment.getUiChangeListener();

                for (NewsFragment.UiChangeListener listener : mUiChangeListener) {

                    if (listener != null)
                        listener.onSearchResult(query);

                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        return true;
    }

}
