package com.codewiththiru.platform.analytics.api

import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import com.codewiththiru.platform.analytics.domain.event.AnalyticsScreen
import com.codewiththiru.platform.analytics.domain.event.AnalyticsUserProperty

/**
 * Interface representing a destination for analytics events.
 */
public interface AnalyticsProvider {
    /** Tracks a specific analytics event. */
    public suspend fun trackEvent(event: AnalyticsEvent)

    /** Tracks a screen view. */
    public suspend fun trackScreen(screen: AnalyticsScreen)

    /** Sets a user property. */
    public suspend fun setUserProperty(property: AnalyticsUserProperty)

    /** Flushes any queued events to the destination. */
    public suspend fun flush()
}
