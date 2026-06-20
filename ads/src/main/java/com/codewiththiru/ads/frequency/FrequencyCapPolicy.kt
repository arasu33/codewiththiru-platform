package com.codewiththiru.ads.frequency

import com.codewiththiru.ads.api.AdType
import kotlinx.serialization.Serializable

/**
 * Defines the frequency capping rules for ads.
 */
@Serializable
data class FrequencyCapPolicy(
    val rules: Map<AdType, AdCapRule> = mapOf(
        AdType.Interstitial to AdCapRule(maxImpressions = 1, durationMinutes = 3),
        AdType.AppOpen to AdCapRule(maxImpressions = 1, durationMinutes = 240), // 4 hours
        AdType.Rewarded to AdCapRule(maxImpressions = Int.MAX_VALUE, durationMinutes = 0), // Unlimited
        AdType.Native to AdCapRule(maxImpressions = Int.MAX_VALUE, durationMinutes = 0),
        AdType.Banner to AdCapRule(maxImpressions = Int.MAX_VALUE, durationMinutes = 0)
    )
)

@Serializable
data class AdCapRule(
    val maxImpressions: Int,
    val durationMinutes: Int
)
