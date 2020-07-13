package dev.ashish.newsapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dev.ashish.newsapp.model.News.Article
import dev.ashish.newsapp.repository.NewsRepository

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: NewsRepository
    private var articleList: LiveData<List<Article?>?>? = null
    val topNews: LiveData<List<Article?>?>?
        get() {
            Log.e("NewsViewModel", "getTopNews: ")
            articleList = repo.getTopNews("in", "ae6c3c0f9d8e485a98fd70edcff81134")
            return articleList
        }

    init {
        repo = NewsRepository.Companion.instance
        //articleList = NewsRepository.getInstance().getTopNews("in", "ae6c3c0f9d8e485a98fd70edcff81134");
    }
}