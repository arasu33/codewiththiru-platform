package com.codewiththiru.notifications.provider

import com.codewiththiru.notifications.api.NotificationPayload
import com.codewiththiru.notifications.api.NotificationResult

interface NotificationProvider {
    suspend fun showNotification(payload: NotificationPayload): NotificationResult
    fun cancelNotification(id: String)
    fun cancelAll()
}
