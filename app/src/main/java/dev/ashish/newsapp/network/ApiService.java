package dev.ashish.newsapp.network;

import dev.ashish.newsapp.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("top-headlines")
    Call<News> getTopNews(@Query("country") String country, @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<News> getTopCategory(@Query("country") String country, @Query("category") String category, @Query("apiKey") String apiKey);
}
