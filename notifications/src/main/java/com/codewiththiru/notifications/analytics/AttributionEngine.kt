package com.codewiththiru.notifications.analytics

import com.codewiththiru.platform.analytics.api.AnalyticsManager
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent

class AttributionEngine(private val analyticsManager: AnalyticsManager) {
    suspend fun attributeConversion(campaignId: String, conversionValue: Double) {
        val event = AnalyticsEvent(
            name = "campaign_conversion",
            params = mapOf(
                "campaign_id" to campaignId,
                "value" to conversionValue
            )
        )
        analyticsManager.track(event)
    }
}
