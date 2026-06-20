package com.codewiththiru.platform.analytics.data.provider

import com.codewiththiru.platform.analytics.api.AnalyticsProvider
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import com.codewiththiru.platform.analytics.domain.event.AnalyticsScreen
import com.codewiththiru.platform.analytics.domain.event.AnalyticsUserProperty

/**
 * A no-op implementation of [AnalyticsProvider].
 */
public class NoOpAnalyticsProvider : AnalyticsProvider {
    override suspend fun trackEvent(event: AnalyticsEvent) { /* No-op */ }
    override suspend fun trackScreen(screen: AnalyticsScreen) { /* No-op */ }
    override suspend fun setUserProperty(property: AnalyticsUserProperty) { /* No-op */ }
    override suspend fun flush() { /* No-op */ }
}
