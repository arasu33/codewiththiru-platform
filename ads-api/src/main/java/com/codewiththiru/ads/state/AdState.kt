package com.codewiththiru.ads.state

/**
 * Represents the deterministic state machine for an Ad.
 */
sealed interface AdState {
    data object Idle : AdState

    data object Loading : AdState

    data object Loaded : AdState

    data object Showing : AdState

    data object Dismissed : AdState

    data class Failed(val error: Throwable) : AdState

    data object Expired : AdState

    data object Used : AdState

    // Blocking states
    data object BlockedByConsent : AdState

    data object BlockedByFrequencyCap : AdState

    data object BlockedByRemoteConfig : AdState
}
