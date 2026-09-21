package com.codewiththiru.platform.analytics.domain.event

/**
 * Represents a screen view in the application.
 *
 * @property name The name of the screen.
 * @property className The associated class name of the screen, if any.
 */
public data class AnalyticsScreen(
    public val name: String,
    public val className: String? = null,
)
