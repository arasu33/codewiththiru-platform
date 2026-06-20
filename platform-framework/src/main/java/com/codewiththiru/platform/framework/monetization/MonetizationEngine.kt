package com.codewiththiru.platform.framework.monetization

data class RevenueProfile(
    val profileId: String,
    val targetLtv: Float,
    val adFrequency: Int
)

enum class MonetizationStrategy {
    ADS_ONLY, FREEMIUM, SUBSCRIPTION_ONLY, HYBRID
}

interface MonetizationManager {
    fun applyStrategy(strategy: MonetizationStrategy, profile: RevenueProfile)
    fun optimizeRevenue()
}
