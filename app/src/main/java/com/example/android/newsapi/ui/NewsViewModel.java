package com.example.android.newsapi.ui;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.newsapi.data.NewsRepository;
import com.example.android.newsapi.model.Article;
import com.example.android.newsapi.network.Specification;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private final NewsRepository mNewsRepository;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        mNewsRepository = NewsRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Article>> getNewsHeadlines(Specification specification) {
        return mNewsRepository.getHeadlines(specification);
    }

}
