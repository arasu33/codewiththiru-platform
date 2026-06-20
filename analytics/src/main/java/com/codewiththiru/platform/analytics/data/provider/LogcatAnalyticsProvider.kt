package com.codewiththiru.platform.analytics.data.provider

import android.util.Log
import com.codewiththiru.platform.analytics.api.AnalyticsProvider
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import com.codewiththiru.platform.analytics.domain.event.AnalyticsScreen
import com.codewiththiru.platform.analytics.domain.event.AnalyticsUserProperty

/**
 * Implementation of [AnalyticsProvider] that outputs to Android Logcat.
 */
public class LogcatAnalyticsProvider : AnalyticsProvider {
    private val tag = "LogcatAnalytics"

    override suspend fun trackEvent(event: AnalyticsEvent) {
        Log.d(tag, "Track Event: ${event.name}, params: ${event.parameters}")
    }

    override suspend fun trackScreen(screen: AnalyticsScreen) {
        Log.d(tag, "Track Screen: ${screen.name}, class: ${screen.className}")
    }

    override suspend fun setUserProperty(property: AnalyticsUserProperty) {
        Log.d(tag, "Set Property: ${property.key} = ${property.value}")
    }

    override suspend fun flush() {
        Log.d(tag, "Flush triggered")
    }
}
