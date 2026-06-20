package com.codewiththiru.notifications.history

import com.codewiththiru.notifications.api.NotificationPayload

data class NotificationHistory(
    val payload: NotificationPayload,
    val deliveredAtMillis: Long,
    val isRead: Boolean = false,
    val isDismissed: Boolean = false
)
