package com.codewiththiru.platform.rating.model

/**
 * Identifies the context/source that triggered the rating prompt for analytics.
 */
enum class RatingTriggerSource {
    AppLaunch,
    LessonCompleted,
    LevelCompleted,
    PurchaseCompleted,
    Manual,
    Custom
}
