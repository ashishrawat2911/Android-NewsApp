package dev.ashish.newsapp.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.ashish.newsapp.R
import dev.ashish.newsapp.adaptor.NewsAdaptor
import dev.ashish.newsapp.broadcastreceiver.ConnectivityBroadcastReceiver
import dev.ashish.newsapp.model.News.Article
import dev.ashish.newsapp.utils.Constants
import dev.ashish.newsapp.viewmodel.MyViewModel
import dev.ashish.newsapp.viewmodel.NewsViewModel
import dev.ashish.newsapp.viewmodel.NewsViewModelFactory
import java.util.*

class NewsListActivity : AppCompatActivity() {
    var newsRecyclerView: RecyclerView? = null
    var newsAdaptor: NewsAdaptor? = null
    var newsList: List<Article>? = null
    var newsType: String? = null
    var progressBar: ProgressBar? = null
    var connectivityBroadcastReceiver: ConnectivityBroadcastReceiver? = null
    private val isBroadcastReceiverRegistered = false
    private val isActivityLoaded = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)
        progressBar = findViewById(R.id.newsListProgressbar)
        progressBar?.setIndeterminate(true)
        val receivedIntent = intent
        newsType = receivedIntent.getStringExtra(Constants.NEWS_TYPE)
        newsRecyclerView = findViewById(R.id.newsListRecyclerView)
        newsList = ArrayList()
        newsAdaptor = NewsAdaptor(this, newsType)
        newsRecyclerView?.setAdapter(newsAdaptor)
        newsRecyclerView
                ?.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        loadData()
    }

    private fun loadData() {
        if (newsType == "top_news") {
            val newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
            newsViewModel.topNews?.observe(this, Observer { articles ->
                Log.e("Main activity", "onChanged: getTopNews")
                if (articles != null) newsAdaptor!!.setNewsList(articles as List<Article>?)
                progressBar!!.visibility = View.GONE
                newsAdaptor!!.notifyDataSetChanged()
            })
        } else {
            val myViewModel = ViewModelProviders.of(this, NewsViewModelFactory(this.application, newsType)).get(MyViewModel::class.java)
            myViewModel.categoriesNews.observe(this, Observer { articles ->
                Log.e("Main activity", "onChanged: getCategoriesNews")
                if (articles != null) newsAdaptor!!.setNewsList(articles as List<Article>?)
                progressBar!!.visibility = View.GONE
                newsAdaptor!!.notifyDataSetChanged()
            })
        }
    }
}