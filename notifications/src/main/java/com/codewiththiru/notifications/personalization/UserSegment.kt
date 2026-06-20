package com.codewiththiru.notifications.personalization

enum class UserSegment {
    BEGINNER,
    ADVANCED,
    INACTIVE,
    POWER_USER,
    LEARNING_STREAK,
    GAMER,
    ALL
}

data class NotificationTargeting(
    val requiredSegments: List<UserSegment> = emptyList(),
    val excludedSegments: List<UserSegment> = emptyList()
)
