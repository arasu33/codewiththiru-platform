package com.codewiththiru.notifications.provider

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.codewiththiru.notifications.api.NotificationPayload
import com.codewiththiru.notifications.api.NotificationResult

class LocalNotificationProvider(
    private val context: Context,
    private val builder: NotificationBuilder,
    private val analyticsProvider: com.codewiththiru.notifications.analytics.NotificationAnalyticsProvider
) : NotificationProvider {

    private val rateLimiterPrefs = context.getSharedPreferences("notification_rate_limiter", Context.MODE_PRIVATE)

    @SuppressLint("MissingPermission")
    override suspend fun showNotification(payload: NotificationPayload): NotificationResult {
        // Basic Rate Limiting: Max 10 per category per hour
        val hourKey = "${payload.category.name}_${System.currentTimeMillis() / (1000 * 60 * 60)}"
        val count = rateLimiterPrefs.getInt(hourKey, 0)
        if (count >= 10) {
            return NotificationResult.Failure(Exception("Rate limit exceeded for category: ${payload.category.name}"))
        }

        return try {
            val notification = builder.build(payload)
            // Use hashcode or payload ID logic
            val notificationId = payload.id.hashCode()
            NotificationManagerCompat.from(context).notify(notificationId, notification)
            
            // Increment rate limit counter
            rateLimiterPrefs.edit().putInt(hourKey, count + 1).apply()
            
            // Track analytics
            analyticsProvider.trackReceived(payload)

            NotificationResult.Success
        } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
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
