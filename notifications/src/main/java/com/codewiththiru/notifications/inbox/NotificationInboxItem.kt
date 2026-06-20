package com.codewiththiru.notifications.inbox

import com.codewiththiru.notifications.api.NotificationPayload
import kotlinx.serialization.Serializable

@Serializable
data class NotificationInboxItem(
    val payload: NotificationPayload,
    val isRead: Boolean = false,
    val isArchived: Boolean = false
)
