package dev.ashish.newsapp.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import dev.ashish.newsapp.R;
import dev.ashish.newsapp.activities.DetailsActivity;
import dev.ashish.newsapp.utils.Constants;
import dev.ashish.newsapp.model.News;

public class NotificationUtils {
    private static final int PENDING_INTENT_ID = 2873;
    private static final String NOTIFICATION_CHANNEL = "notification_channel";
    private static final String NOTIFICATION_CHANNEL_NAME = "Primary";

    public static void notifyNewsToUser(Context context, News.Article article) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentTitle(article.getTitle())
                .setContentText(article.getDescription())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(contentIntent(context))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationManager.notify(1, builder.build());
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, DetailsActivity.class);
        startActivityIntent.putExtra(Constants.NEWS_TYPE, "top_news");
        startActivityIntent.putExtra(Constants.POSITION, 0);
        return PendingIntent.getActivity(
                context,
                PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
