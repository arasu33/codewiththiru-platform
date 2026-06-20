package com.codewiththiru.notifications.provider.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.codewiththiru.notifications.api.NotificationPayload
import com.codewiththiru.notifications.api.NotificationPriority
import com.codewiththiru.notifications.api.NotificationCategory

class FirebaseNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val payload = NotificationParser.parse(message)
        // In a real app, this would be injected or routed to DefaultNotificationManager.
        // For now, it passes the payload down.
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Send new token to backend
    }
}
