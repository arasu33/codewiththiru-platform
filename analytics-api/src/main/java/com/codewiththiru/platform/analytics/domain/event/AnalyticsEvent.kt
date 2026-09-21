package com.codewiththiru.platform.analytics.domain.event

/**
 * Represents a single analytics event to be tracked.
 *
 * @property name The name of the event. Must follow naming constraints.
 * @property parameters A map of key-value pairs providing context for the event.
 * @property timestamp The time the event was generated, in milliseconds since epoch.
 */
public data class AnalyticsEvent(
    public val name: String,
    public val parameters: Map<String, Any?> = emptyMap(),
    public val timestamp: Long = System.currentTimeMillis(),
)
