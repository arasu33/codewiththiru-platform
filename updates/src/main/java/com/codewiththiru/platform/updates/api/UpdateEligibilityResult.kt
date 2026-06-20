package com.codewiththiru.platform.updates.api

/**
 * Represents the outcome of checking whether the user is eligible for an update prompt.
 */
sealed interface UpdateEligibilityResult {
    data object Eligible : UpdateEligibilityResult
    data object CooldownActive : UpdateEligibilityResult
    data object AlreadyShown : UpdateEligibilityResult
    data object ForceUpdateRequired : UpdateEligibilityResult
    data object NoUpdateAvailable : UpdateEligibilityResult
}
