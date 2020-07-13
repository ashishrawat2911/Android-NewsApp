package dev.ashish.newsapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton
import dev.ashish.newsapp.R
import dev.ashish.newsapp.activities.DetailsActivity
import dev.ashish.newsapp.model.News
import dev.ashish.newsapp.utils.Constants
import dev.ashish.newsapp.viewmodel.MyViewModel
import dev.ashish.newsapp.viewmodel.NewsViewModel
import dev.ashish.newsapp.viewmodel.NewsViewModelFactory
import retrofit2.Call

class DetailsActivity : AppCompatActivity() {
    var newsViewModel: NewsViewModel? = null
    var newsCall: Call<News>? = null
    var url: String? = null
    var newsType: String? = null
    var newsPosition = 0
    var titleTextView: TextView? = null
    var descTextView: TextView? = null
    var detailsImageView: ImageView? = null
    var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        progressBar = findViewById(R.id.detailsProgressbar)
        progressBar?.setIndeterminate(true)
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        titleTextView = findViewById(R.id.detailsTitleTextView)
        descTextView = findViewById(R.id.detailsDescTextView)
        detailsImageView = findViewById(R.id.detailsImageView)
        val receivedIntent = intent
        newsType = receivedIntent.getStringExtra(Constants.NEWS_TYPE)
        newsPosition = receivedIntent.getIntExtra(Constants.POSITION, -1)
        loadData()
        val floatingActionButton = findViewById<FloatingActionButton>(R.id.moreNews)
        floatingActionButton.setOnClickListener {
            val i = Intent(this@DetailsActivity, WebViewActivity::class.java)
            i.putExtra(Constants.URL, url)
            startActivity(i)
        }
    }

    //Fetching all the data from the News Api asynchronously using Retrofit
    private fun loadData() {
        if (newsType == "top_news") {
            newsViewModel?.topNews?.observe(this, Observer { articles ->
                progressBar!!.visibility = View.GONE
                setDetails(articles!![newsPosition]?.title, articles!![newsPosition]?.description, articles!![newsPosition]?.urlToImage)
                url = articles!![newsPosition]?.url
            })
        } else {
            val myViewModel = ViewModelProviders.of(this, NewsViewModelFactory(this.application, newsType)).get(MyViewModel::class.java)
            myViewModel.categoriesNews.observe(this, Observer { articles ->
                progressBar!!.visibility = View.GONE
                setDetails(articles!![newsPosition]?.title, articles[newsPosition]?.description, articles[newsPosition]?.urlToImage)
                url = articles[newsPosition]?.url
            })
        }
    }

    //Data is fetched from two apis (this method is for code re-usability)
    private fun setDetails(title: String?, desc: String?, detailsImage: String?) {
        titleTextView!!.text = title
        descTextView!!.text = desc
        if (detailsImage == null) {
            detailsImageView!!.setImageResource(R.drawable.no_image_available)
        } else Glide.with(this@DetailsActivity)
                .asBitmap()
                .load(detailsImage)
                .into(detailsImageView!!)
    }
}