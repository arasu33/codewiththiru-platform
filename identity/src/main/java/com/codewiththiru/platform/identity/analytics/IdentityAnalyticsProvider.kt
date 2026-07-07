package com.codewiththiru.platform.identity.analytics

interface IdentityAnalyticsProvider {
    fun logEvent(
        name: String,
        params: Map<String, Any> = emptyMap(),
    )

    fun setUserProperty(
        name: String,
        value: String,
    )
}
