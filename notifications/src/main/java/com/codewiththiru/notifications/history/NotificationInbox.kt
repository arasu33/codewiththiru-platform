package com.codewiththiru.notifications.history

import com.codewiththiru.notifications.repository.NotificationHistoryRepository
import kotlinx.coroutines.flow.Flow

class NotificationInbox(private val repository: NotificationHistoryRepository) {
    fun getInboxItems(): Flow<List<NotificationHistory>> {
        return repository.getHistory()
    }

    fun getUnreadCount(): Flow<Int> {
        return repository.getUnreadCount()
    }

    suspend fun markRead(notificationId: String) {
        repository.markAsRead(notificationId)
    }

    suspend fun markDismissed(notificationId: String) {
        repository.markAsDismissed(notificationId)
    }
}
