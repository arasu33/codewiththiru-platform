package com.codewiththiru.notifications.config

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.codewiththiru.notifications.api.NotificationCategory

class NotificationChannelManager(private val context: Context) {

    fun createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = NotificationManagerCompat.from(context)
            val channels = NotificationCategory.values().map { category ->
                NotificationChannel(
                    category.channelId,
                    category.channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = category.channelDesc
                }
            }
            channels.forEach { notificationManager.createNotificationChannel(it) }
        }
    }

    fun getActiveChannels(): List<String> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = NotificationManagerCompat.from(context)
            return notificationManager.notificationChannels
                .filter { it.importance != NotificationManager.IMPORTANCE_NONE }
                .map { it.id }
        }
        return NotificationCategory.values().map { it.channelId }
    }
}
