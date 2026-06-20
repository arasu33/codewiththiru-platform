package com.codewiththiru.notifications.remoteconfig

import com.codewiththiru.remoteconfig.api.RemoteConfigManager

class RemoteConfigNotificationController(private val remoteConfigManager: RemoteConfigManager) {

    fun isNotificationsEnabled(): Boolean {
        return remoteConfigManager.getBoolean("notifications_enabled", true)
    }

    fun isQuietHoursEnabled(): Boolean {
        return remoteConfigManager.getBoolean("notifications_quiet_hours_enabled", false)
    }

    fun getQuietHoursStart(): Int {
        // e.g., 22 for 10 PM
        return remoteConfigManager.getLong("notifications_quiet_hours_start", 22L).toInt()
    }

    fun getQuietHoursEnd(): Int {
        // e.g., 7 for 7 AM
        return remoteConfigManager.getLong("notifications_quiet_hours_end", 7L).toInt()
    }

    fun isCampaignEnabled(campaignId: String): Boolean {
        return remoteConfigManager.getBoolean("campaign_enabled_$campaignId", true)
    }
}
