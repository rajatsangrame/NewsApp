package com.example.android.newsapi.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.android.newsapi.model.Article;
import com.example.android.newsapi.model.ResponseArticles;
import com.example.android.newsapi.model.ResponseSources;
import com.example.android.newsapi.model.Source;
import com.example.android.newsapi.network.NewsApi;
import com.example.android.newsapi.network.NewsApiClient;
import com.example.android.newsapi.network.Specification;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsRepository {

    private static final String TAG = "NewsRepository";
    private static final Object LOCK = new Object();
    private static NewsRepository sInstance;
    private final NewsApi mNewsApi;
    private final MutableLiveData<List<Article>> mNetworkArticleLiveData;

    private final MutableLiveData<List<Source>> mNetworkSourceLiveData;

    public synchronized static NewsRepository getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NewsRepository(context);
            }
        }
        return sInstance;
    }

    private NewsRepository(Context context) {

        mNewsApi = NewsApiClient.getInstance(context);
        mNetworkArticleLiveData = new MutableLiveData<>();
        mNetworkSourceLiveData = new MutableLiveData<>();
        // Set observables here to save This Data in DB
    }


    public LiveData<List<Article>> getHeadlines(final Specification specs) {
        final MutableLiveData<List<Article>> networkArticleLiveData = new MutableLiveData<>();

        Call<ResponseArticles> networkCall = mNewsApi.getHeadlines(
                specs.getCategory(),
                specs.getCountry()
        );

        networkCall.enqueue(new Callback<ResponseArticles>() {
            @Override
            public void onResponse(Call<ResponseArticles> call, Response<ResponseArticles> response) {

                if (response.body() != null) {
                    networkArticleLiveData.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResponseArticles> call, Throwable t) {

                Log.i(TAG, "onFailure: ");
            }
        });
        return networkArticleLiveData;
    }

    public LiveData<List<Source>> getSources(final Specification specs) {

        final MutableLiveData<List<Source>> networkSourcesLiveData = new MutableLiveData<>();
        Call<ResponseSources> networkCall = mNewsApi.getSources(
                specs.getCategory(),
                specs.getCountry(),
                null
        );

        networkCall.enqueue(new Callback<ResponseSources>() {
            @Override
            public void onResponse(@NonNull Call<ResponseSources> call, @NonNull Response<ResponseSources> response) {

                Log.i(TAG, "onResponse: ");

                if (response.body() != null) {
                    networkSourcesLiveData.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseSources> call, @NonNull Throwable t) {

                Log.i(TAG, "onFailure: ");
            }
        });
        return networkSourcesLiveData;

    }

    public MutableLiveData<List<Article>> getNetworkArticleLiveData() {
        return mNetworkArticleLiveData;
    }

    public MutableLiveData<List<Source>> getNetworkSourceLiveData() {
        return mNetworkSourceLiveData;
    }
}