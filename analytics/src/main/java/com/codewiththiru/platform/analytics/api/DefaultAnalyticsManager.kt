package com.codewiththiru.platform.analytics.api

import com.codewiththiru.platform.analytics.data.dispatcher.AnalyticsDispatcher
import com.codewiththiru.platform.analytics.data.queue.AnalyticsQueue
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import com.codewiththiru.platform.analytics.domain.event.AnalyticsScreen
import com.codewiththiru.platform.analytics.domain.event.AnalyticsUserProperty
import com.codewiththiru.platform.analytics.domain.validation.AnalyticsValidator

/**
 * Default implementation of [AnalyticsManager].
 * Coordinates validation, consent checking, and queueing.
 */
public class DefaultAnalyticsManager(
    private val queue: AnalyticsQueue,
    private val dispatcher: AnalyticsDispatcher,
    private val consentManager: AnalyticsConsentManager,
) : AnalyticsManager {
    override suspend fun track(event: AnalyticsEvent) {
        if (consentManager.getConsent() == ConsentState.Denied) return

        // PII and constraints validation
        val sanitizedEvent = AnalyticsValidator.validateAndSanitize(event)

        queue.enqueue(sanitizedEvent)
    }

    override suspend fun trackScreen(screen: AnalyticsScreen) {
        if (consentManager.getConsent() == ConsentState.Denied) return

        val eventParameters =
            mutableMapOf<String, Any>(
                "screen_name" to screen.name,
            )
        screen.className?.let { eventParameters["screen_class"] = it }

        val event =
            AnalyticsEvent(
                name = "screen_view",
                parameters = eventParameters,
            )
        val sanitizedEvent = AnalyticsValidator.validateAndSanitize(event)
        queue.enqueue(sanitizedEvent)
    }

    override suspend fun setUserProperty(property: AnalyticsUserProperty) {
        if (consentManager.getConsent() == ConsentState.Denied) return

        val event =
            AnalyticsEvent(
                name = "user_property_set",
                parameters =
                    mapOf(
                        "property_name" to property.key,
                        "property_value" to property.value,
                    ),
            )
        val sanitizedEvent = AnalyticsValidator.validateAndSanitize(event)
        queue.enqueue(sanitizedEvent)
    }

    override suspend fun flush() {
        if (consentManager.getConsent() != ConsentState.Denied) {
            dispatcher.flush()
        }
    }
}
