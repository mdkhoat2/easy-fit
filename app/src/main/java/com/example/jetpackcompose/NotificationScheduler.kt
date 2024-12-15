package com.example.jetpackcompose

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class NotificationScheduler {
    // singleton
    companion object {
        private var instance: NotificationScheduler? = null

        fun getInstance(): NotificationScheduler {
            if (instance == null) {
                instance = NotificationScheduler()
            }
            return instance as NotificationScheduler
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleNotification(context: Context, timeInMillis: Long, title: String, message: String) {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("message", message)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
    }

}