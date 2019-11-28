package com.example.android.newsapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rajat Sangrame on 26/11/19.
 * http://github.com/rajatsangrame
 */
public class ResponseSources {

    @SerializedName("status")
    private boolean status;
    @SerializedName("totalResult")
    private int totalResults;
    @SerializedName("sources")
    private List<Source> data;

    public boolean isStatus() {
        return status;
    }

    public int getMessage() {
        return totalResults;
    }

    public List<Source> getData() {
        return data;
    }
}
