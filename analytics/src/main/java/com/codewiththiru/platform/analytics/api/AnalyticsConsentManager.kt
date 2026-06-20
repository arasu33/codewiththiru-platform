package com.codewiththiru.platform.analytics.api

/**
 * Interface for managing user consent for analytics.
 */
public interface AnalyticsConsentManager {
    /** Grants consent for tracking. */
    public suspend fun grant()

    /** Denies consent for tracking. */
    public suspend fun deny()

    /** Returns the current consent state. */
    public suspend fun getConsent(): ConsentState
}
