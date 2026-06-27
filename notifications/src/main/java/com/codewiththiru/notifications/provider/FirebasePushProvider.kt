package com.codewiththiru.notifications.provider

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

class FirebasePushProvider : PushNotificationProvider {

    private val firebaseMessaging: FirebaseMessaging? by lazy {
        try {
            FirebaseMessaging.getInstance()
        } catch (e: Exception) {
            android.util.Log.e("CWT_PLATFORM", "Firebase Messaging is not initialized.", e)
            null
        }
    }

    override suspend fun subscribeToTopic(topic: String): Boolean {
        val fcm = firebaseMessaging ?: return false
        return try {
            fcm.subscribeToTopic(topic).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun unsubscribeFromTopic(topic: String): Boolean {
        val fcm = firebaseMessaging ?: return false
        return try {
            fcm.unsubscribeFromTopic(topic).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getToken(): String? {
        val fcm = firebaseMessaging ?: return null
        return try {
            fcm.token.await()
        } catch (e: Exception) {
            null
        }
    }

    override fun deleteToken() {
        val fcm = firebaseMessaging ?: return
        try {
            fcm.deleteToken()
        } catch (e: Exception) {
            android.util.Log.e("CWT_PLATFORM", "Failed to delete Firebase push token", e)
        }
    }
}
