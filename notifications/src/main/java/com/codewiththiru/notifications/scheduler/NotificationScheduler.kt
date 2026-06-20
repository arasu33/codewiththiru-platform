package com.codewiththiru.notifications.scheduler

import com.codewiththiru.notifications.api.NotificationPayload

interface NotificationScheduler {
    fun schedule(payload: NotificationPayload, triggerAtMillis: Long)
    fun scheduleRepeating(payload: NotificationPayload, intervalMillis: Long)
    fun cancel(id: String)
    fun cancelAll()
}
