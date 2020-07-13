package dev.ashish.newsapp.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import dev.ashish.newsapp.R
import dev.ashish.newsapp.activities.DetailsActivity
import dev.ashish.newsapp.model.News.Article
import dev.ashish.newsapp.utils.Constants

object NotificationUtils {
    private const val PENDING_INTENT_ID = 2873
    private const val NOTIFICATION_CHANNEL = "notification_channel"
    private const val NOTIFICATION_CHANNEL_NAME = "Primary"
    fun notifyNewsToUser(context: Context, article: Article?) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentTitle(article?.title)
                .setContentText(article?.description)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(contentIntent(context))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)
        builder.priority = NotificationCompat.PRIORITY_HIGH
        notificationManager.notify(1, builder.build())
    }

    private fun contentIntent(context: Context): PendingIntent {
        val startActivityIntent = Intent(context, DetailsActivity::class.java)
        startActivityIntent.putExtra(Constants.NEWS_TYPE, "top_news")
        startActivityIntent.putExtra(Constants.POSITION, 0)
        return PendingIntent.getActivity(
                context,
                PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
    }
}