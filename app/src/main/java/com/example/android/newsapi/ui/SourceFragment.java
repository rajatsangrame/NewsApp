package com.example.android.newsapi.ui;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.newsapi.R;
import com.example.android.newsapi.adapter.SourceAdapter;
import com.example.android.newsapi.databinding.SourceFragmentBinding;
import com.example.android.newsapi.model.Source;
import com.example.android.newsapi.network.Specification;

import java.util.List;
import java.util.Locale;

public class SourceFragment extends Fragment implements SourceAdapter.SourceAdapterListener {

    private SourceViewModel mViewModel;
    private SourceFragmentBinding mBinding;

    public static SourceFragment newInstance() {
        return new SourceFragment();
    }

    private final SourceAdapter sourceAdapter = new SourceAdapter(null, this);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.source_fragment, container, false);

        setupViewModel();
        mBinding.rvSources.setAdapter(sourceAdapter);

        return mBinding.getRoot();
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SourceViewModel.class);
        Specification specification = new Specification();
        specification.setLanguage(Locale.getDefault().getLanguage());
        specification.setCountry(null);
        mViewModel.getSource(specification).observe(this, new Observer<List<Source>>() {
            @Override
            public void onChanged(@Nullable List<Source> sources) {
                if (sources != null) {
                    sourceAdapter.setSources(sources);
                    mBinding.rvSources.setAdapter(sourceAdapter);
                }
            }
        });
    }

    @Override
    public void onSourceButtonClicked(Source source) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(source.getUrl()));
        startActivity(intent);
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.option_search);
        if(item!=null)
            item.setVisible(false);
    }
}
