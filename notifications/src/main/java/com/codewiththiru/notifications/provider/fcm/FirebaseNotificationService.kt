package com.codewiththiru.notifications.provider.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.codewiththiru.notifications.api.NotificationPayload
import com.codewiththiru.notifications.api.NotificationPriority
import com.codewiththiru.notifications.api.NotificationCategory
import kotlinx.coroutines.launch

class FirebaseNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val payload = NotificationParser.parse(message)
        val provider = com.codewiththiru.notifications.provider.AndroidNotificationProvider(applicationContext)
        kotlinx.coroutines.runBlocking {
            provider.showNotification(payload)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Send new token to backend
    }
}
