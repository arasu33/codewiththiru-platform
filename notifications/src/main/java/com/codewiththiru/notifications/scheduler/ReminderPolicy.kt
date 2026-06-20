package com.codewiththiru.notifications.scheduler

import com.codewiththiru.notifications.api.NotificationPayload

data class ReminderPolicy(
    val id: String,
    val payload: NotificationPayload,
    val rule: ReminderRule,
    val trigger: ReminderTrigger
)
