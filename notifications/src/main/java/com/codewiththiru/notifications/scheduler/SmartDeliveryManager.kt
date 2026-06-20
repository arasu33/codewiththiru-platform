package com.codewiththiru.notifications.scheduler

import com.codewiththiru.notifications.api.NotificationPayload
import com.codewiththiru.notifications.config.NotificationConfig
import java.util.Calendar

class SmartDeliveryManager(private val config: NotificationConfig) {
    fun shouldDeliverNow(payload: NotificationPayload): Boolean {
        val quietPolicy = config.quietHoursPolicy
        if (!quietPolicy.enabled) return true

        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        
        val currentMins = currentHour * 60 + currentMinute
        val startMins = quietPolicy.startHour * 60 + quietPolicy.startMinute
        val endMins = quietPolicy.endHour * 60 + quietPolicy.endMinute
        
        val inQuietHours = if (startMins <= endMins) {
            currentMins in startMins..endMins
        } else {
            currentMins >= startMins || currentMins <= endMins
        }
        
        if (inQuietHours && !quietPolicy.allowHighPriority) {
            return false
        }
        
        if (inQuietHours && quietPolicy.allowHighPriority && payload.priority.ordinal < 3) {
            return false
        }

        return true
    }
}
