package com.codewiththiru.notifications.repository

import com.codewiththiru.notifications.api.NotificationPayload
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun saveNotification(payload: NotificationPayload)
    suspend fun getNotification(id: String): NotificationPayload?
    fun getAllNotifications(): Flow<List<NotificationPayload>>
    suspend fun deleteNotification(id: String)
    suspend fun clearAll()
}
