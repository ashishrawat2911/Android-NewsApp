package dev.ashish.newsapp.utils

import android.content.Context
import android.util.Log
import com.firebase.jobdispatcher.*
import dev.ashish.newsapp.service.NotificationReminderJobService

object ReminderUtilities {
    private const val NOTIFICATION_REMINDER_TAG = "notification_reminder_tag"
    private const val REMINDER_INTERVAL_MINUTES = 1
    private const val REMINDER_INTERVAL_SECONDS = 1
    private const val SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS
    fun scheduleNotificationReminder(context: Context) {
        val driver: Driver = GooglePlayDriver(context)
        val firebaseJobDispatcher = FirebaseJobDispatcher(driver)
        val job = firebaseJobDispatcher.newJobBuilder()
                .setService(NotificationReminderJobService::class.java)
                .setTag(NOTIFICATION_REMINDER_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS,
                        REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build()
        firebaseJobDispatcher.schedule(job)
        Log.e("scheduleNotification", "scheduleNotificationReminder: ")
    }
}