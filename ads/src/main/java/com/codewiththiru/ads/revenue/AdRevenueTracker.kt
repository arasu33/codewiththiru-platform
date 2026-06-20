package com.codewiththiru.ads.revenue

import com.codewiththiru.ads.api.AdType

/**
 * Interface for tracking revenue-related events.
 */
interface AdRevenueTracker {

    /**
     * Called when an ad records an impression.
     */
    fun trackImpression(adType: AdType, network: String, placement: String)

    /**
     * Called when a user clicks on an ad.
     */
    fun trackClick(adType: AdType, network: String, placement: String)

    /**
     * Called when a paid event occurs, recording the revenue details.
     */
    fun trackPaidEvent(
        adType: AdType,
        valueMicros: Long,
        currencyCode: String,
        network: String,
        placement: String
    )

    /**
     * Called when a user earns a reward.
     */
    fun trackReward(
        adType: AdType,
        rewardType: String,
        rewardAmount: Int,
        network: String,
        placement: String
    )
}
