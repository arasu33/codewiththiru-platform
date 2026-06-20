package com.codewiththiru.notifications.provider

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.codewiththiru.notifications.api.NotificationPayload
import com.codewiththiru.notifications.api.NotificationResult

class LocalNotificationProvider(
    private val context: Context,
    private val builder: NotificationBuilder
) : NotificationProvider {

    @SuppressLint("MissingPermission")
    override suspend fun showNotification(payload: NotificationPayload): NotificationResult {
        return try {
            val notification = builder.build(payload)
            // Use hashcode or payload ID logic
            val notificationId = payload.id.hashCode()
            NotificationManagerCompat.from(context).notify(notificationId, notification)
            NotificationResult.Success
        } catch (e: Exception) {
            NotificationResult.Failure(e)
        }
    }

    override fun cancelNotification(id: String) {
        NotificationManagerCompat.from(context).cancel(id.hashCode())
    }

    override fun cancelAll() {
        NotificationManagerCompat.from(context).cancelAll()
    }
}
