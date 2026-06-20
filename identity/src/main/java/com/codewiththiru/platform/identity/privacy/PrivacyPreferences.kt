package com.codewiththiru.platform.identity.privacy

data class PrivacyPreferences(
    val allowAnalyticsTracking: Boolean = true,
    val allowPersonalizedAds: Boolean = true,
    val profileVisibility: Visibility = Visibility.PUBLIC
) {
    enum class Visibility {
        PUBLIC, FRIENDS_ONLY, PRIVATE
    }
}
