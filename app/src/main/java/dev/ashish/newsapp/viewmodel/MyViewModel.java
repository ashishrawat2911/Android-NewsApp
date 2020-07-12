package dev.ashish.newsapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import dev.ashish.newsapp.model.News;
import dev.ashish.newsapp.repository.NewsRepository;

import java.util.List;

public  class MyViewModel extends ViewModel {

    private LiveData<List<News.Article>> articleList;

    MyViewModel(Application application, String newsType) {
        articleList = NewsRepository.getInstance().getCategoryNews("in", newsType, "ae6c3c0f9d8e485a98fd70edcff81134");
    }

    public LiveData<List<News.Article>> getCategoriesNews() {

        Log.e("NewsViewModel", "getTopNews: ");
        return articleList;
    }
}
