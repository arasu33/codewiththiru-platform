package com.codewiththiru.notifications.analytics

import com.codewiththiru.platform.analytics.api.AnalyticsManager
import com.codewiththiru.platform.analytics.domain.event.AnalyticsUserProperty

class EngagementTracker(private val analyticsManager: AnalyticsManager) {
    suspend fun updateEngagementScore(userId: String, score: Float) {
        val property = AnalyticsUserProperty(
            name = "notification_engagement_score",
            value = score.toString()
        )
        analyticsManager.setUserProperty(property)
    }
}
