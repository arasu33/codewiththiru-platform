package com.codewiththiru.platform.analytics.data.provider

import android.util.Log
import com.codewiththiru.platform.analytics.api.AnalyticsProvider
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import com.codewiththiru.platform.analytics.domain.event.AnalyticsScreen
import com.codewiththiru.platform.analytics.domain.event.AnalyticsUserProperty
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

/**
 * An [AnalyticsProvider] that fans out to multiple underlying destinations.
 * "Failure of one provider must never block others"
 */
internal class CompositeAnalyticsProvider(
    private val providers: List<AnalyticsProvider>,
) : AnalyticsProvider {
    @Suppress("TooGenericExceptionCaught")
    override suspend fun trackEvent(event: AnalyticsEvent) {
        supervisorScope {
            providers
                .map { provider ->
                    launch {
                        try {
                            provider.trackEvent(event)
                        } catch (e: Exception) {
                            Log.e("CompositeAnalytics", "Provider failed to track event", e)
                        }
                    }
                }.joinAll()
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override suspend fun trackScreen(screen: AnalyticsScreen) {
        supervisorScope {
            providers
                .map { provider ->
                    launch {
                        try {
                            provider.trackScreen(screen)
                        } catch (e: Exception) {
                            Log.e("CompositeAnalytics", "Provider failed to track screen", e)
                        }
                    }
                }.joinAll()
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override suspend fun setUserProperty(property: AnalyticsUserProperty) {
        supervisorScope {
            providers
                .map { provider ->
                    launch {
                        try {
                            provider.setUserProperty(property)
                        } catch (e: Exception) {
                            Log.e("CompositeAnalytics", "Provider failed to set user property", e)
                        }
                    }
                }.joinAll()
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override suspend fun flush() {
        supervisorScope {
            providers
                .map { provider ->
                    launch {
                        try {
                            provider.flush()
                        } catch (e: Exception) {
                            Log.e("CompositeAnalytics", "Provider failed to flush", e)
                        }
                    }
                }.joinAll()
        }
    }
}
