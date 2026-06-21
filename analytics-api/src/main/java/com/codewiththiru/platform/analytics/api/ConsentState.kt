package com.codewiththiru.platform.analytics.api

/**
 * Represents the current state of user consent for analytics tracking.
 */
public enum class ConsentState {
    /** Consent has been explicitly granted. */
    Granted,
    
    /** Consent has been explicitly denied. */
    Denied,
    
    /** Consent has not yet been determined. */
    Unknown
}
