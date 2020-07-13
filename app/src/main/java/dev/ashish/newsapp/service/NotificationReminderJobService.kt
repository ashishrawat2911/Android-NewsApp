package dev.ashish.newsapp.service

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import dev.ashish.newsapp.R
import dev.ashish.newsapp.model.News
import dev.ashish.newsapp.network.ApiClient
import dev.ashish.newsapp.network.ApiService
import dev.ashish.newsapp.notification.NotificationUtils
import dev.ashish.newsapp.service.NotificationReminderJobService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationReminderJobService : JobService() {
    var topNews: String? = null
    var prefs: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    var apiService: ApiService? = null
    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        apiService = ApiClient.client?.create(ApiService::class.java)
    }

    @SuppressLint("StaticFieldLeak")
    override fun onStartJob(job: JobParameters): Boolean {
        topNews = prefs!!.getString(KEY_TOP_NEWS, null)

        apiService!!.getTopNews("in", resources.getString(R.string.api_key)).enqueue(object : Callback<News?> {
            override fun onResponse(call: Call<News?>, response: Response<News?>) {
                if (!response.isSuccessful && response.body()?.articles == null) {
                    return
                }
                Log.e("taking response ", "taking response in service ")
                try {
                    val list = response.body()?.articles
                    Log.e("list", "onResponse: " + list!!.size)
                    val title = list[0].title
                    if (title != null) {
                        Log.e("title", "onResponse: " + "title is not null")
                        Log.e("title", "onResponse: $title")
                        if (title != topNews) {
                            Log.e("title", "onResponse: title not equalstop news ")
                            NotificationUtils.notifyNewsToUser(this@NotificationReminderJobService, list[0])
                            editor = prefs!!.edit()
                            editor?.putString(KEY_TOP_NEWS, title)
                            editor?.apply()
                        } else {
                            Log.e("title", "onResponse: title =top news ")
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<News?>, t: Throwable) {
                Log.e("taking response", "onFailure:taking response in service ")
            }
        })
        return true
    }

    override fun onStopJob(job: JobParameters): Boolean {
        return true
    }

    companion object {
        private const val KEY_TOP_NEWS = "topnews"
    }
}

