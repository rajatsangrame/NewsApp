package com.example.android.newsapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rajat Sangrame on 26/11/19.
 * http://github.com/rajatsangrame
 */
public class ResponseArticles {

    @SerializedName("status")
    private boolean status;
    @SerializedName("articles")
    private List<Article> data;

    public boolean isStatus() {
        return status;
    }

    public List<Article> getData() {
        return data;
    }
}
