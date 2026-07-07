package com.codewiththiru.platform.analytics.data.provider

import com.codewiththiru.platform.analytics.api.AnalyticsProvider
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import com.codewiththiru.platform.analytics.domain.event.AnalyticsScreen
import com.codewiththiru.platform.analytics.domain.event.AnalyticsUserProperty
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Provider implementation that delegates to Firebase Analytics.
 * All Firebase APIs are strictly encapsulated within this class.
 */
public class FirebaseAnalyticsProvider(
    private val firebaseAnalytics: FirebaseAnalytics,
) : AnalyticsProvider {
    override suspend fun trackEvent(event: AnalyticsEvent) {
        val bundle = FirebaseEventMapper.toBundle(event)
        firebaseAnalytics.logEvent(event.name, bundle)
    }

    override suspend fun trackScreen(screen: AnalyticsScreen) {
        val bundle =
            android.os.Bundle().apply {
                putString(FirebaseAnalytics.Param.SCREEN_NAME, screen.name)
                screen.className?.let { putString(FirebaseAnalytics.Param.SCREEN_CLASS, it) }
            }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    override suspend fun setUserProperty(property: AnalyticsUserProperty) {
        firebaseAnalytics.setUserProperty(property.key, property.value)
    }

    override suspend fun flush() {
        // Firebase handles its own flushing asynchronously.
        // We do not have explicit control over its internal SQLite database dispatch.
    }
}
