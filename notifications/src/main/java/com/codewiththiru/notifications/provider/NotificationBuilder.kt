package com.codewiththiru.notifications.provider

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.codewiththiru.notifications.api.NotificationPayload
import com.codewiththiru.notifications.config.NotificationConfig

class NotificationBuilder @JvmOverloads constructor(
    private val context: Context,
    private val config: NotificationConfig,
    private val ioDispatcher: kotlinx.coroutines.CoroutineDispatcher = kotlinx.coroutines.Dispatchers.IO
) {

    suspend fun build(payload: NotificationPayload): Notification {
        val builder = NotificationCompat.Builder(context, payload.category.channelId)
            .setSmallIcon(config.smallIconResId)
            .setContentTitle(payload.title)
            .setContentText(payload.body)
            .setAutoCancel(true)

        // Handle Grouping
        payload.groupId?.let { groupId ->
            builder.setGroup(groupId)
            // A separate summary notification is typically needed for groups,
            // but this flags it as part of the group.
        }

        // Handle Rich Media (Image) vs BigText
        val imageUrl = payload.imageUrl
        if (!imageUrl.isNullOrEmpty()) {
            val bitmap = downloadBitmap(imageUrl)
            if (bitmap != null) {
                builder.setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .setBigContentTitle(payload.title)
                )
            } else {
                builder.setStyle(NotificationCompat.BigTextStyle().bigText(payload.body))
            }
        } else {
            builder.setStyle(NotificationCompat.BigTextStyle().bigText(payload.body))
        }

        // Handle Actions
        payload.actions.forEach { action ->
            // For now, we use a dummy PendingIntent. 
            // In a real app, this would route to a BroadcastReceiver or Activity.
            val intent = android.content.Intent().apply {
                action.deepLink?.let { data = android.net.Uri.parse(it) }
            }
            val pendingIntent = android.app.PendingIntent.getActivity(
                context, action.actionId.hashCode(), intent,
                android.app.PendingIntent.FLAG_IMMUTABLE or android.app.PendingIntent.FLAG_UPDATE_CURRENT
            )
            builder.addAction(0, action.title, pendingIntent) // 0 is icon res id (deprecated/optional)
        }

        return builder.build()
    }

    private suspend fun downloadBitmap(urlStr: String): android.graphics.Bitmap? = kotlinx.coroutines.withContext(ioDispatcher) {
        try {
            val url = java.net.URL(urlStr)
            val connection = url.openConnection() as java.net.HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            android.graphics.BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException || e is kotlin.coroutines.cancellation.CancellationException) throw e
            null
        }
    }
}
