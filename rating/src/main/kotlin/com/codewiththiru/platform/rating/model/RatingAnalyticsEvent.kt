package com.codewiththiru.platform.rating.model

/**
 * Defines standard analytics events for the rating module.
 */
enum class RatingAnalyticsEvent {
    PromptShown,
    StarSelected,
    SubmitClicked,
    ReviewLaunched,
    FeedbackRedirected,
    Dismissed,
    EligibilityFailed
}
