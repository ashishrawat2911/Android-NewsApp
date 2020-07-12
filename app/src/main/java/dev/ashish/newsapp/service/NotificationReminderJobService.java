package dev.ashish.newsapp.service;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import dev.ashish.newsapp.R;
import dev.ashish.newsapp.model.News;
import dev.ashish.newsapp.network.ApiClient;
import dev.ashish.newsapp.network.ApiService;

import dev.ashish.newsapp.notification.NotificationUtils;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationReminderJobService extends JobService {
    private static final String KEY_TOP_NEWS = "topnews";
    String topNews = null;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        apiService = ApiClient.getClient().create(ApiService.class);

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(JobParameters job) {
        topNews = prefs.getString(KEY_TOP_NEWS, null);
        apiService.getTopNews("in", getResources().getString(R.string.api_key)).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (!response.isSuccessful() && response.body().getArticles() == null) {
                    return;
                }
                Log.e("taking response ", "taking response in service ");
                try {
                    List<News.Article> list = response.body().getArticles();
                    Log.e("list", "onResponse: " + list.size());
                    String title = list.get(0).getTitle();
                    if (title != null) {
                        Log.e("title", "onResponse: " + "title is not null");
                        Log.e("title", "onResponse: " + title);
                        if (!title.equals(topNews)) {
                            Log.e("title", "onResponse: title not equalstop news ");
                            NotificationUtils.notifyNewsToUser(NotificationReminderJobService.this, list.get(0));
                            editor = prefs.edit();
                            editor.putString(KEY_TOP_NEWS, title);
                            editor.apply();
                        } else {
                            Log.e("title", "onResponse: title =top news ");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.e("taking response", "onFailure:taking response in service ");
            }
        });


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }

}
