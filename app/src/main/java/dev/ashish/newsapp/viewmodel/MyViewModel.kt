package dev.ashish.newsapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.ashish.newsapp.model.News.Article
import dev.ashish.newsapp.repository.NewsRepository

class MyViewModel internal constructor(application: Application?, newsType: String?) : ViewModel() {
    private val articleList: LiveData<List<Article?>?>
    val categoriesNews: LiveData<List<Article?>?>
        get() {
            Log.e("NewsViewModel", "getTopNews: ")
            return articleList
        }

    init {
        articleList = NewsRepository.Companion.instance.getCategoryNews("in", newsType, "ae6c3c0f9d8e485a98fd70edcff81134")
    }
}