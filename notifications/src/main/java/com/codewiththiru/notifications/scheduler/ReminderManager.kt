package com.codewiththiru.notifications.scheduler

interface ReminderManager {
    fun scheduleReminder(policy: ReminderPolicy)
    fun cancelReminder(reminderId: String)
    fun getActiveReminders(): List<ReminderPolicy>
}
