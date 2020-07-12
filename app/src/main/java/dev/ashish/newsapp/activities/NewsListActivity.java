package dev.ashish.newsapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import dev.ashish.newsapp.R;
import dev.ashish.newsapp.adaptor.NewsAdaptor;
import dev.ashish.newsapp.broadcastreceiver.ConnectivityBroadcastReceiver;
import dev.ashish.newsapp.model.News;
import dev.ashish.newsapp.utils.Constants;
import dev.ashish.newsapp.viewmodel.MyViewModel;
import dev.ashish.newsapp.viewmodel.NewsViewModel;
import dev.ashish.newsapp.viewmodel.NewsViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class NewsListActivity extends AppCompatActivity {


    RecyclerView newsRecyclerView;
    NewsAdaptor newsAdaptor;
    List<News.Article> newsList;
    String newsType;
    ProgressBar progressBar;
    ConnectivityBroadcastReceiver connectivityBroadcastReceiver;
    private boolean isBroadcastReceiverRegistered;
    private boolean isActivityLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_list);
        progressBar = findViewById(R.id.newsListProgressbar);
        progressBar.setIndeterminate(true);

        Intent receivedIntent = getIntent();
        newsType = receivedIntent.getStringExtra(Constants.NEWS_TYPE);
        newsRecyclerView = findViewById(R.id.newsListRecyclerView);
        newsList = new ArrayList<>();
        newsAdaptor = new NewsAdaptor(this, newsType);
        newsRecyclerView.setAdapter(newsAdaptor);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        loadData();
    }

    private void loadData() {
        if (newsType.equals("top_news")) {
            NewsViewModel newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
            newsViewModel.getTopNews().observe(this, new Observer<List<News.Article>>() {
                @Override
                public void onChanged(@Nullable List<News.Article> articles) {
                    Log.e("Main activity", "onChanged: getTopNews");
                    if (articles != null)
                        newsAdaptor.setNewsList(articles);
                    progressBar.setVisibility(View.GONE);
                    newsAdaptor.notifyDataSetChanged();
                }
            });

        } else {
            MyViewModel myViewModel = ViewModelProviders.of(this, new NewsViewModelFactory(this.getApplication(), newsType)).get(MyViewModel.class);
            myViewModel.getCategoriesNews().observe(this, new Observer<List<News.Article>>() {
                @Override
                public void onChanged(@Nullable List<News.Article> articles) {
                    Log.e("Main activity", "onChanged: getCategoriesNews");
                    if (articles != null)
                        newsAdaptor.setNewsList(articles);
                    progressBar.setVisibility(View.GONE);
                    newsAdaptor.notifyDataSetChanged();

                }
            });

        }
    }


}
