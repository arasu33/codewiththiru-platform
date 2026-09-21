package com.codewiththiru.platform.analytics.domain.event

/**
 * Represents a user property for segmentation and targeting.
 *
 * @property key The identifier for the user property.
 * @property value The value associated with the user property.
 */
public data class AnalyticsUserProperty(
    public val key: String,
    public val value: String,
)
