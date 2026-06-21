package com.codewiththiru.notifications.api

import kotlinx.coroutines.flow.StateFlow

interface NotificationManager {
    val state: StateFlow<NotificationState>
    fun initialize()
    suspend fun showNotification(payload: NotificationPayload): NotificationResult
    suspend fun scheduleNotification(payload: NotificationPayload, triggerAtMillis: Long): NotificationResult
    fun cancelNotification(id: String)
    fun cancelAll()
    fun getActiveChannels(): List<String>
}
