package com.codewiththiru.platform.analytics.api

import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import com.codewiththiru.platform.analytics.domain.event.AnalyticsScreen
import com.codewiththiru.platform.analytics.domain.event.AnalyticsUserProperty

/**
 * Main entry point for analytics tracking.
 */
public interface AnalyticsManager {
    /** Tracks a specific analytics event. */
    public suspend fun track(event: AnalyticsEvent)

    /** Tracks a screen view. */
    public suspend fun trackScreen(screen: AnalyticsScreen)

    /** Sets a user property. */
    public suspend fun setUserProperty(property: AnalyticsUserProperty)

    /** Forces a flush of the underlying analytics queue. */
    public suspend fun flush()
}
