package com.codewiththiru.notifications.scheduler

sealed class ReminderTrigger {
    data class AbsoluteTime(val timestampMillis: Long) : ReminderTrigger()
    data class Inactivity(val daysSinceLastActive: Int) : ReminderTrigger()
    data class Milestone(val currentProgress: Int, val targetProgress: Int) : ReminderTrigger()
}
