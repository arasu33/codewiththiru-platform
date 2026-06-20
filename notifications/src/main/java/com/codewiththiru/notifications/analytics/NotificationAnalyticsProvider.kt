package com.codewiththiru.notifications.analytics

import com.codewiththiru.platform.analytics.api.AnalyticsManager
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import com.codewiththiru.notifications.api.NotificationPayload

class NotificationAnalyticsProvider(private val analyticsManager: AnalyticsManager) {

    suspend fun trackReceived(payload: NotificationPayload) {
        val event = AnalyticsEvent(
            name = "notification_received",
            params = mapOf(
                "notification_id" to payload.id,
                "category" to payload.category.name,
                "priority" to payload.priority.name
            )
        )
        analyticsManager.track(event)
    }

    suspend fun trackOpened(payload: NotificationPayload) {
        val event = AnalyticsEvent(
            name = "notification_opened",
            params = mapOf(
                "notification_id" to payload.id,
                "category" to payload.category.name,
                "has_deep_link" to (payload.deepLink != null).toString()
            )
        )
        analyticsManager.track(event)
    }

    suspend fun trackDismissed(notificationId: String) {
        val event = AnalyticsEvent(
            name = "notification_dismissed",
            params = mapOf("notification_id" to notificationId)
        )
        analyticsManager.track(event)
    }
}
