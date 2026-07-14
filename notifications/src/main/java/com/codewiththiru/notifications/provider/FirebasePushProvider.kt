package com.codewiththiru.notifications.provider

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

class FirebasePushProvider : PushNotificationProvider {

    private val firebaseMessaging: FirebaseMessaging? by lazy {
        try {
            FirebaseMessaging.getInstance()
        } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: Exception) {
            android.util.Log.e("CWT_PLATFORM", "Firebase Messaging is not initialized.", e)
            null
        }
    }

    private suspend fun <T> withRetry(
        maxRetries: Int = 3,
        initialDelayMs: Long = 1000,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelayMs
        var lastException: Exception? = null
        for (attempt in 0 until maxRetries) {
            try {
                return block()
            } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: Exception) {
                lastException = e
                if (attempt < maxRetries - 1) {
                    kotlinx.coroutines.delay(currentDelay)
                    currentDelay *= 2
                }
            }
        }
        throw lastException ?: Exception("Unknown error in withRetry")
    }

    override suspend fun subscribeToTopic(topic: String): Boolean {
        val fcm = firebaseMessaging ?: return false
        return try {
            withRetry { fcm.subscribeToTopic(topic).await() }
            true
        } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: Exception) {
            false
        }
    }

    override suspend fun unsubscribeFromTopic(topic: String): Boolean {
        val fcm = firebaseMessaging ?: return false
        return try {
            withRetry { fcm.unsubscribeFromTopic(topic).await() }
            true
        } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: Exception) {
            false
        }
    }

    override suspend fun getToken(): String? {
        val fcm = firebaseMessaging ?: return null
        return try {
            withRetry { fcm.token.await() }
        } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: Exception) {
            null
        }
    }

    override fun deleteToken() {
        val fcm = firebaseMessaging ?: return
        try {
            fcm.deleteToken()
        } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: Exception) {
            android.util.Log.e("CWT_PLATFORM", "Failed to delete Firebase push token", e)
        }
    }
}
