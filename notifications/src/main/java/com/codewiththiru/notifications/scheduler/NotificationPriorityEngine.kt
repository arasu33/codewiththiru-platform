package com.codewiththiru.notifications.scheduler

import com.codewiththiru.notifications.api.NotificationPayload
import com.codewiththiru.notifications.api.NotificationPriority

class NotificationPriorityEngine {
    fun calculateEffectivePriority(payload: NotificationPayload, userEngagementScore: Float): NotificationPriority {
        // High engagement users might get normal priorities bumped down if fatigued, etc.
        // Simplified for this phase
        return payload.priority
    }
}
