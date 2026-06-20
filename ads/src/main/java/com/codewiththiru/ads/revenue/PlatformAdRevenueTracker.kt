package com.codewiththiru.ads.revenue

import com.codewiththiru.ads.analytics.AdsAnalyticsProvider
import com.codewiththiru.ads.api.AdType
import com.codewiththiru.platform.analytics.api.AnalyticsManager
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Concrete implementation that maps ad revenue and monetization events
 * to the centralized AnalyticsManager.
 */
class PlatformAdRevenueTracker(
    private val analyticsManager: AnalyticsManager,
    private val coroutineScope: CoroutineScope
) : AdRevenueTracker {

    override fun trackImpression(adType: AdType, network: String, placement: String) {
        logEvent(
            AdsAnalyticsProvider.EVENT_AD_IMPRESSION,
            mapOf(
                "ad_type" to adType.name,
                "ad_network" to network,
                "placement" to placement
            )
        )
    }

    override fun trackClick(adType: AdType, network: String, placement: String) {
        logEvent(
            AdsAnalyticsProvider.EVENT_AD_CLICKED,
            mapOf(
                "ad_type" to adType.name,
                "ad_network" to network,
                "placement" to placement
            )
        )
    }

    override fun trackPaidEvent(
        adType: AdType,
        valueMicros: Long,
        currencyCode: String,
        network: String,
        placement: String
    ) {
        logEvent(
            AdsAnalyticsProvider.EVENT_AD_REVENUE,
            mapOf(
                "ad_type" to adType.name,
                "value_micros" to valueMicros,
                "currency_code" to currencyCode,
                "ad_network" to network,
                "placement" to placement
            )
        )
    }

    override fun trackReward(
        adType: AdType,
        rewardType: String,
        rewardAmount: Int,
        network: String,
        placement: String
    ) {
        logEvent(
            AdsAnalyticsProvider.EVENT_AD_REWARDED,
            mapOf(
                "ad_type" to adType.name,
                "reward_type" to rewardType,
                "reward_amount" to rewardAmount,
                "ad_network" to network,
                "placement" to placement
            )
        )
    }

    private fun logEvent(eventName: String, parameters: Map<String, Any>) {
        coroutineScope.launch {
            analyticsManager.track(AnalyticsEvent(eventName, parameters))
        }
    }
}
