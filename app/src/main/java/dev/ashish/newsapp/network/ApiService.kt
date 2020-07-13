package dev.ashish.newsapp.network

import dev.ashish.newsapp.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    fun getTopNews(@Query("country") country: String?, @Query("apiKey") apiKey: String?): Call<News?>

    @GET("top-headlines")
    fun getTopCategory(@Query("country") country: String?, @Query("category") category: String?, @Query("apiKey") apiKey: String?): Call<News?>
}