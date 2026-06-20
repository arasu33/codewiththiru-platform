package com.codewiththiru.notifications.scheduler

interface ReminderScheduler {
    fun schedule(policy: ReminderPolicy)
    fun cancel(reminderId: String)
}
