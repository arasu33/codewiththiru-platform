package com.codewiththiru.remoteconfig.api

sealed class RemoteConfigKey<T>(
    val key: String,
    val defaultValue: T
) {
    // Boolean Keys
    object AdsEnabled : RemoteConfigKey<Boolean>("ads_enabled", false)
    object RatingEnabled : RemoteConfigKey<Boolean>("rating_enabled", false)
    object UpdatesEnabled : RemoteConfigKey<Boolean>("updates_enabled", false)
    object RewardedEnabled : RemoteConfigKey<Boolean>("rewarded_enabled", false)
    object NativeAdsEnabled : RemoteConfigKey<Boolean>("native_ads_enabled", false)
    object BillingEnabled : RemoteConfigKey<Boolean>("billing_enabled", false)
    object AnalyticsEnabled : RemoteConfigKey<Boolean>("analytics_enabled", true)
    object FeedbackEnabled : RemoteConfigKey<Boolean>("feedback_enabled", true)
    object CouponsEnabled : RemoteConfigKey<Boolean>("coupons_enabled", false)
    object NotificationsEnabled : RemoteConfigKey<Boolean>("notifications_enabled", false)
    object GamificationEnabled : RemoteConfigKey<Boolean>("gamification_enabled", false)
    object MoreAppsEnabled : RemoteConfigKey<Boolean>("more_apps_enabled", false)

    // Int / Long Keys
    object ForceUpdateVersion : RemoteConfigKey<Int>("force_update_version", 0)
    object MinimumSupportedVersion : RemoteConfigKey<Int>("minimum_supported_version", 0)
    object InterstitialFrequency : RemoteConfigKey<Long>("interstitial_frequency", 300L)
    
    // String Keys
    // Add any future string keys here
}
