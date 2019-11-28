package com.example.android.newsapi.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.AndroidViewModel;


import com.example.android.newsapi.data.NewsRepository;
import com.example.android.newsapi.model.Source;
import com.example.android.newsapi.network.Specification;

import java.util.List;

public class SourceViewModel extends AndroidViewModel {

    private NewsRepository mNewsRepository;

    public SourceViewModel(@NonNull Application application) {
        super(application);
        mNewsRepository = NewsRepository.getInstance(getApplication());
    }

    LiveData<List<Source>> getSource(Specification specification) {
        return mNewsRepository.getSources(specification);
    }
}
