package com.example.android.newsapi.network;


import com.example.android.newsapi.BuildConfig;
import com.example.android.newsapi.model.Article;
import com.example.android.newsapi.model.ResponseArticles;
import com.example.android.newsapi.model.ResponseSources;
import com.example.android.newsapi.model.Source;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Rajat Sangrame on 26/11/19.
 * http://github.com/rajatsangrame
 */
public interface NewsApi {
    String API_KEY = BuildConfig.NewsApiKey;

    @Headers("X-Api-Key:" + API_KEY)
    @GET("/v2/top-headlines")
    Call<ResponseArticles> getHeadlines(
            @Query("category") String category,
            @Query("country") String country
    );

    @Headers("X-Api-Key:" + API_KEY)
    @GET("/v2/top-headlines")
    Call<ResponseArticles> getHeadlines(
            @Query("sources") String source
    );

    @Headers("X-Api-Key:" + API_KEY)
    @GET("/v2/sources")
    Call<ResponseSources> getSources(
            @Query("category") String category,
            @Query("country") String country,
            @Query("language") String language
    );

    @Headers("X-Api-Key:" + API_KEY)
    @GET("/v2/sources")
    Call<ResponseSources> getSources(
    );

    enum Category {
        business("Business"),
        entertainment("Entertainment"),
        general("General"),
        health("Health"),
        science("Science"),
        sports("Sports"),
        technology("Technology");

        public final String title;

        Category(String title) {
            this.title = title;
        }
    }


    class Param {
        public static final String ACHIEVEMENT_ID = "achievement_id";
        public static final String CHARACTER = "character";
        public static final String TRAVEL_CLASS = "travel_class";
        public static final String CONTENT_TYPE = "content_type";
        public static final String CURRENCY = "currency";
        public static final String COUPON = "coupon";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";
        public static final String FLIGHT_NUMBER = "flight_number";
        public static final String GROUP_ID = "group_id";
        public static final String ITEM_CATEGORY = "item_category";
        public static final String ITEM_ID = "item_id";
        public static final String ITEM_LOCATION_ID = "item_location_id";
        public static final String ITEM_NAME = "item_name";
        public static final String LOCATION = "location";
        public static final String LEVEL = "level";
        public static final String LEVEL_NAME = "level_name";
    }
}