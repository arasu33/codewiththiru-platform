package com.codewiththiru.platform.updates.api

/**
 * One-time UI events related to the update flow.
 */
sealed interface UpdateEffect {
    data object LaunchFlexibleUpdate : UpdateEffect

    data object LaunchImmediateUpdate : UpdateEffect

    data object ShowWhatsNew : UpdateEffect

    data object ShowForceUpdate : UpdateEffect

    data object RestartApp : UpdateEffect

    data class ShowError(
        val message: String,
    ) : UpdateEffect

    data object None : UpdateEffect
}
