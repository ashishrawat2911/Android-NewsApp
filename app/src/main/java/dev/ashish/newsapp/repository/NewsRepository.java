package dev.ashish.newsapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import dev.ashish.newsapp.model.News;
import dev.ashish.newsapp.network.ApiClient;
import dev.ashish.newsapp.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private ApiService apiService;

    private static class SingletonHelper {
        private static final NewsRepository INSTANCE = new NewsRepository();
    }

    public static NewsRepository getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private NewsRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }


    public LiveData<List<News.Article>> getTopNews(String country, String apiKey) {
        final MutableLiveData<List<News.Article>> data = new MutableLiveData<>();
        apiService.getTopNews(country, apiKey).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                Log.e("NewsRepo", "onResponse: getTopNews ");
                data.setValue(response.body().getArticles());
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public List<News.Article> getTopNewsForNotification(String country, String apiKey) {

        final List<News.Article> finalArticles = null;
        apiService.getTopNews(country, apiKey).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (!response.isSuccessful() && response.body().getArticles() == null) {
                    return;
                }

                finalArticles.addAll(response.body().getArticles());

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
        return finalArticles;
    }

    public LiveData<List<News.Article>> getCategoryNews(String country, String category, String apiKey) {

        final MutableLiveData<List<News.Article>> data = new MutableLiveData<>();

        apiService.getTopCategory(country, category, apiKey).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                Log.e("NewsRepo", "onResponse: getCategoryNews ");
                data.setValue(response.body().getArticles());
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
