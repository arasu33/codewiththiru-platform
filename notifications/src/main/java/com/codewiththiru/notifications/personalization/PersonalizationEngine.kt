package com.codewiththiru.notifications.personalization

class PersonalizationEngine {
    // In a real app, this would observe the user's state from the core repository
    private var activeSegments = setOf(UserSegment.ALL, UserSegment.BEGINNER)

    fun setUserSegments(segments: Set<UserSegment>) {
        activeSegments = segments + UserSegment.ALL
    }

    fun isTargeted(targeting: NotificationTargeting): Boolean {
        if (targeting.requiredSegments.isNotEmpty()) {
            val hasRequired = targeting.requiredSegments.any { it in activeSegments }
            if (!hasRequired) return false
        }

        if (targeting.excludedSegments.isNotEmpty()) {
            val hasExcluded = targeting.excludedSegments.any { it in activeSegments }
            if (hasExcluded) return false
        }

        return true
    }
}
