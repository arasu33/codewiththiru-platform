package com.codewiththiru.notifications.provider

interface PushNotificationProvider {
    suspend fun subscribeToTopic(topic: String): Boolean
    suspend fun unsubscribeFromTopic(topic: String): Boolean
    suspend fun getToken(): String?
    fun deleteToken()
}
