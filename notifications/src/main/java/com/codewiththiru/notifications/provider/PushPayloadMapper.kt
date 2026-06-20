package com.codewiththiru.notifications.provider

import com.codewiththiru.notifications.api.NotificationCategory
import com.codewiththiru.notifications.api.NotificationPayload
import com.codewiththiru.notifications.api.NotificationPriority
import java.util.UUID

class PushPayloadMapper {
    fun map(data: Map<String, String>): NotificationPayload {
        return NotificationPayload(
            id = data["id"] ?: UUID.randomUUID().toString(),
            title = data["title"] ?: "",
            body = data["message"] ?: "",
            category = runCatching { NotificationCategory.valueOf(data["category"] ?: "") }.getOrDefault(NotificationCategory.GENERAL),
            priority = runCatching { NotificationPriority.valueOf(data["priority"] ?: "") }.getOrDefault(NotificationPriority.DEFAULT),
            deepLink = data["deepLink"],
            imageUrl = data["imageUrl"],
            data = data
        )
    }
}
