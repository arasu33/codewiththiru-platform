package com.codewiththiru.notifications.provider

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.codewiththiru.notifications.api.NotificationPayload
import com.codewiththiru.notifications.api.NotificationResult

class AndroidNotificationProvider(private val context: Context) : NotificationProvider {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun showNotification(payload: NotificationPayload): NotificationResult {
        if (payload.isSilent) return NotificationResult.Suppressed

        val intent = payload.deepLink?.let {
            Intent(Intent.ACTION_VIEW, android.net.Uri.parse(it)).apply {
                setPackage(context.packageName)
            }
        } ?: context.packageManager.getLaunchIntentForPackage(context.packageName) ?: Intent()
        
        val pendingIntent = PendingIntent.getActivity(
            context, 
            payload.id.hashCode(), 
            intent, 
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, payload.category.channelId)
            .setContentTitle(payload.title)
            .setContentText(payload.body)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Placeholder
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        notificationManager.notify(payload.id.hashCode(), builder.build())
        return NotificationResult.Success
    }

    override fun cancelNotification(id: String) {
        notificationManager.cancel(id.hashCode())
    }

    override fun cancelAll() {
        notificationManager.cancelAll()
    }
}
