package dev.ashish.newsapp.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import dev.ashish.newsapp.service.NotificationReminderJobService;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class ReminderUtilities {

    private static final String NOTIFICATION_REMINDER_TAG = "notification_reminder_tag";
    private static final int REMINDER_INTERVAL_MINUTES = 1;
    private static final int REMINDER_INTERVAL_SECONDS = 1;
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    public static void scheduleNotificationReminder(@NonNull Context context) {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);
        Job job = firebaseJobDispatcher.newJobBuilder()
                .setService(NotificationReminderJobService.class)
                .setTag(NOTIFICATION_REMINDER_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS,
                        REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();
        firebaseJobDispatcher.schedule(job);
        Log.e("scheduleNotification", "scheduleNotificationReminder: " );
    }
}
