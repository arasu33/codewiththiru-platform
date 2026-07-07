package com.codewiththiru.platform.rating.model

/**
 * The outcome of attempting to launch the Google Play In-App Review API.
 */
sealed interface ReviewLaunchResult {
    data object Success : ReviewLaunchResult

    data object Cancelled : ReviewLaunchResult

    data object PlayServicesUnavailable : ReviewLaunchResult

    data class Failed(
        val exceptionMessage: String?,
    ) : ReviewLaunchResult
}
