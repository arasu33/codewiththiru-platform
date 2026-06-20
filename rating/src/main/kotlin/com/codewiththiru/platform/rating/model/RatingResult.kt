package com.codewiththiru.platform.rating.model

/**
 * The outcome of the user's interaction with the rating prompt.
 */
sealed interface RatingResult {
    data class SuccessReview(val stars: Int) : RatingResult
    data class FeedbackRequested(val stars: Int) : RatingResult
    data object Dismissed : RatingResult
    data object CooldownActive : RatingResult
    data object ConditionsNotMet : RatingResult
    data class Failed(val reason: String) : RatingResult
}
