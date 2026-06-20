package com.codewiththiru.notifications.provider

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

class FirebasePushProvider : PushNotificationProvider {

    override suspend fun subscribeToTopic(topic: String): Boolean {
        return try {
            FirebaseMessaging.getInstance().subscribeToTopic(topic).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun unsubscribeFromTopic(topic: String): Boolean {
        return try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getToken(): String? {
        return try {
            FirebaseMessaging.getInstance().token.await()
        } catch (e: Exception) {
            null
        }
    }

    override fun deleteToken() {
        FirebaseMessaging.getInstance().deleteToken()
    }
}
