package com.codewiththiru.notifications.provider.fcm

import com.google.firebase.messaging.RemoteMessage
import com.codewiththiru.notifications.api.NotificationPayload
import com.codewiththiru.notifications.api.NotificationPriority
import com.codewiththiru.notifications.api.NotificationCategory
import java.util.UUID

object NotificationParser {
    fun parse(message: RemoteMessage): NotificationPayload {
        val data = message.data
        return NotificationPayload(
            id = message.messageId ?: UUID.randomUUID().toString(),
            title = message.notification?.title ?: data["title"] ?: "",
            body = message.notification?.body ?: data["body"] ?: "",
            deepLink = data["deepLink"],
            priority = NotificationPriority.DEFAULT,
            category = NotificationCategory.GENERAL,
            imageUrl = message.notification?.imageUrl?.toString() ?: data["imageUrl"],
            data = data,
            isSilent = data["silent"]?.toBoolean() ?: false
        )
    }
}
