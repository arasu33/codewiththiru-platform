package com.codewiththiru.platform.rating.model

/**
 * Represents the evaluation result of whether a user should see the rating prompt.
 */
sealed interface RatingEligibilityResult {
    data object Eligible : RatingEligibilityResult
    data object CooldownActive : RatingEligibilityResult
    data class MinimumLaunchesNotMet(val current: Int, val required: Int) : RatingEligibilityResult
    data class MinimumDaysNotMet(val current: Long, val required: Int) : RatingEligibilityResult
    data class MinimumEventsNotMet(val current: Int, val required: Int) : RatingEligibilityResult
    data object Disabled : RatingEligibilityResult
}
