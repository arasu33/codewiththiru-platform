package com.codewiththiru.notifications.repository

import com.codewiththiru.notifications.history.NotificationHistory
import kotlinx.coroutines.flow.Flow

interface NotificationHistoryRepository {
    suspend fun saveHistory(history: NotificationHistory)
    suspend fun markAsRead(notificationId: String)
    suspend fun markAsDismissed(notificationId: String)
    fun getHistory(): Flow<List<NotificationHistory>>
    fun getUnreadCount(): Flow<Int>
}
