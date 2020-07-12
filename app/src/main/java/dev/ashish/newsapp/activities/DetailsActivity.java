package dev.ashish.newsapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import dev.ashish.newsapp.R;
import dev.ashish.newsapp.model.News;
import dev.ashish.newsapp.utils.Constants;
import dev.ashish.newsapp.viewmodel.MyViewModel;
import dev.ashish.newsapp.viewmodel.NewsViewModel;
import dev.ashish.newsapp.viewmodel.NewsViewModelFactory;
import com.bumptech.glide.Glide;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;

public class DetailsActivity extends AppCompatActivity {
    NewsViewModel newsViewModel;

    Call<News> newsCall;

    String url;

    String newsType;
    int newsPosition;

    TextView titleTextView, descTextView;

    ImageView detailsImageView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        progressBar = findViewById(R.id.detailsProgressbar);
        progressBar.setIndeterminate(true);
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        titleTextView = findViewById(R.id.detailsTitleTextView);
        descTextView = findViewById(R.id.detailsDescTextView);
        detailsImageView = findViewById(R.id.detailsImageView);

        Intent receivedIntent = getIntent();
        newsType = receivedIntent.getStringExtra(Constants.NEWS_TYPE);
        newsPosition = receivedIntent.getIntExtra(Constants.POSITION, -1);
        loadData();
        FloatingActionButton floatingActionButton = findViewById(R.id.moreNews);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailsActivity.this, WebViewActivity.class);
                i.putExtra(Constants.URL, url);
                startActivity(i);
            }
        });
    }

    //Fetching all the data from the News Api asynchronously using Retrofit
    private void loadData() {
        if (newsType.equals("top_news")) {
            newsViewModel.getTopNews().observe(this, new Observer<List<News.Article>>() {
                @Override
                public void onChanged(@Nullable List<News.Article> articles) {
                    progressBar.setVisibility(View.GONE);
                    setDetails(articles.get(newsPosition).getTitle(), articles.get(newsPosition).getDescription(), articles.get(newsPosition).getUrlToImage());
                    url = articles.get(newsPosition).getUrl();
                }
            });
        } else {
            MyViewModel myViewModel = ViewModelProviders.of(this, new NewsViewModelFactory(this.getApplication(), newsType)).get(MyViewModel.class);
            myViewModel.getCategoriesNews().observe(this, new Observer<List<News.Article>>() {
                @Override
                public void onChanged(@Nullable List<News.Article> articles) {
                    progressBar.setVisibility(View.GONE);
                    setDetails(articles.get(newsPosition).getTitle(), articles.get(newsPosition).getDescription(), articles.get(newsPosition).getUrlToImage());
                    url = articles.get(newsPosition).getUrl();
                }
            });

        }
    }

    //Data is fetched from two apis (this method is for code re-usability)
    private void setDetails(String title, String desc, String detailsImage) {
        titleTextView.setText(title);
        descTextView.setText(desc);
        if (detailsImage == null) {
            detailsImageView.setImageResource(R.drawable.no_image_available);
        } else
            Glide.with(DetailsActivity.this)
                    .asBitmap()
                    .load(detailsImage)
                    .into(detailsImageView);

    }

}
