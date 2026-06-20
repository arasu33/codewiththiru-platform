package com.codewiththiru.platform.identity.analytics

class UserLifecycleTracker(
    private val analytics: IdentityAnalyticsProvider
) {
    fun trackAccountCreated(userId: String) {
        analytics.logEvent("account_created", mapOf("userId" to userId))
        analytics.setUserProperty("account_status", "active")
    }

    fun trackAccountDeleted(userId: String) {
        analytics.logEvent("account_deleted", mapOf("userId" to userId))
        analytics.setUserProperty("account_status", "deleted")
    }
}
