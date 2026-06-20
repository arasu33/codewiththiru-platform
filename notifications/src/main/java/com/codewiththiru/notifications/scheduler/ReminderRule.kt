package com.codewiththiru.notifications.scheduler

sealed class ReminderRule {
    object Daily : ReminderRule()
    object Weekly : ReminderRule()
    data class CustomInterval(val hours: Long) : ReminderRule()
    data class EventBased(val eventName: String) : ReminderRule()
}
