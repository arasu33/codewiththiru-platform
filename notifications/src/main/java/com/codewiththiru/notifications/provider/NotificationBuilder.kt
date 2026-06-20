package com.codewiththiru.notifications.provider

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.codewiththiru.notifications.api.NotificationPayload
import com.codewiththiru.notifications.config.NotificationConfig

class NotificationBuilder(
    private val context: Context,
    private val config: NotificationConfig
) {

    fun build(payload: NotificationPayload): Notification {
        return NotificationCompat.Builder(context, payload.category.channelId)
            .setSmallIcon(config.smallIconResId)
            .setContentTitle(payload.title)
            .setContentText(payload.message)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(payload.message))
            .build()
    }
}
