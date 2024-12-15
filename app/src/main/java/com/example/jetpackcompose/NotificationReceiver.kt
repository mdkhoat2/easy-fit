package com.example.jetpackcompose

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

class NotificationReceiver : BroadcastReceiver() {
    private var isChannelCreated = false
    companion object {
        private var instance: NotificationReceiver? = null

        fun getInstance(): NotificationReceiver {
            if (instance == null) {
                instance = NotificationReceiver()
            }
            return instance as NotificationReceiver
        }
    }



    @SuppressLint("NotificationPermission")
    fun showNotification(context: Context, title: String, message: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        Log.d("NotificationReceiver", "NotificationManager: $notificationManager")
        val channelId = "default_channel_id"

        if (!isChannelCreated) {
            val channelName = "Default Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            channel.description = "This is the default notification channel"
            notificationManager.createNotificationChannel(channel)
            isChannelCreated = true
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.icon_forum) // Replace with your notification icon
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "No Title"
        val message = intent.getStringExtra("message") ?: "No Message"
        showNotification(context, title, message)
    }
}
