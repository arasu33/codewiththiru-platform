package com.codewiththiru.platform.analytics.domain.validation

import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent

/**
 * Validates that an event conforms to the registered schema.
 * Prevents magic strings and undocumented event keys.
 */
public object AnalyticsEventRegistry {
    // In a real system, this could be populated dynamically from a remote JSON schema.
    // For now, it represents a hardcoded list of approved base schemas.
    private val approvedEvents = setOf(
        "app_opened",
        "screen_view",
        "user_property_set",
        "lesson_completed",
        "ad_impression",
        "purchase_completed",
        "coupon_redeemed",
        "rating_submitted"
    )

    /**
     * Checks if the event name is recognized in the global catalog.
     */
    public fun isEventRegistered(eventName: String): Boolean {
        return approvedEvents.contains(eventName.lowercase())
    }

    /**
     * Optional strict validation. Returns false if the event is unregistered.
     */
    public fun validateStrict(event: AnalyticsEvent): Boolean {
        return isEventRegistered(event.name)
    }
}
