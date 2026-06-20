package com.codewiththiru.ads.consent

import kotlinx.coroutines.flow.StateFlow

/**
 * State of consent collection.
 */
enum class ConsentState {
    Unknown,
    Required,
    Granted,
    Denied,
    NotRequired
}

/**
 * Interface for managing user consent.
 */
interface ConsentManager {

    val consentState: StateFlow<ConsentState>

    /**
     * Initializes the consent manager and requests the latest consent information.
     */
    suspend fun requestConsent()

    /**
     * Refreshes the consent state.
     */
    suspend fun refreshConsent()

    /**
     * Resets the user's consent status.
     */
    fun resetConsent()

    /**
     * Persists the current consent state.
     */
    suspend fun persistConsent(state: ConsentState)
}
