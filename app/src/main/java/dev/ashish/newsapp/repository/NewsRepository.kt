package dev.ashish.newsapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.ashish.newsapp.model.News
import dev.ashish.newsapp.model.News.Article
import dev.ashish.newsapp.network.ApiClient
import dev.ashish.newsapp.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository private constructor() {
    private val apiService: ApiService

    private object SingletonHelper {
        val INSTANCE = NewsRepository()
    }

    fun getTopNews(country: String?, apiKey: String?): LiveData<List<Article?>?> {
        val data = MutableLiveData<List<Article?>?>()
        apiService.getTopNews(country, apiKey).enqueue(object : Callback<News?> {
            override fun onResponse(call: Call<News?>, response: Response<News?>) {
                Log.e("NewsRepo", "onResponse: getTopNews ")
                data.value = response.body()?.articles
            }

            override fun onFailure(call: Call<News?>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }

    fun getTopNewsForNotification(country: String?, apiKey: String?): List<Article>? {
        var finalArticles: List<Article>? = null
        apiService.getTopNews(country, apiKey).enqueue(object : Callback<News?> {
            override fun onResponse(call: Call<News?>, response: Response<News?>) {
                if (!response.isSuccessful && response.body()?.articles == null) {
                    return
                }
                finalArticles = response.body()?.articles
            }

            override fun onFailure(call: Call<News?>, t: Throwable) {}
        })
        return finalArticles
    }

    fun getCategoryNews(country: String?, category: String?, apiKey: String?): LiveData<List<Article?>?> {
        val data = MutableLiveData<List<Article?>?>()
        apiService.getTopCategory(country, category, apiKey).enqueue(object : Callback<News?> {
            override fun onResponse(call: Call<News?>, response: Response<News?>) {
                Log.e("NewsRepo", "onResponse: getCategoryNews ")
                data.value = response.body()?.articles
            }

            override fun onFailure(call: Call<News?>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }

    companion object {
        val instance: NewsRepository
            get() = SingletonHelper.INSTANCE
    }

    init {
        apiService = ApiClient.client?.create(ApiService::class.java)!!
    }
}