package com.codewiththiru.platform.rating.policy

/**
 * Rules that must be met before a user is eligible to be prompted.
 */
data class RatingTriggerRules(
    val minimumAppLaunches: Int = 5,
    val minimumDaysInstalled: Int = 3,
    val requiredSignificantEvents: Int = 1,
)

/**
 * Policies dictating how long to wait before re-prompting.
 */
data class RatingCooldownPolicy(
    val daysAfterDismissal: Int = 14,
    val daysAfterFeedbackRedirect: Int = 30,
    val daysAfterPlayReviewLaunch: Int = 90,
)
