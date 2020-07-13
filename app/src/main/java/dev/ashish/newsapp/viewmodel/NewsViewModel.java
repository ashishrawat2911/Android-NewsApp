package dev.ashish.newsapp.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import android.util.Log;


import dev.ashish.newsapp.repository.NewsRepository;
import dev.ashish.newsapp.model.News;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private final NewsRepository repo;
    private LiveData<List<News.Article>> articleList;


    public  NewsViewModel(@NonNull Application application) {
        super(application);
        repo = NewsRepository.getInstance();
        //articleList = NewsRepository.getInstance().getTopNews("in", "ae6c3c0f9d8e485a98fd70edcff81134");
    }

    public LiveData<List<News.Article>> getTopNews() {

        Log.e("NewsViewModel", "getTopNews: ");

        articleList=repo.getTopNews("in", "ae6c3c0f9d8e485a98fd70edcff81134");
        return articleList;
    }


}
