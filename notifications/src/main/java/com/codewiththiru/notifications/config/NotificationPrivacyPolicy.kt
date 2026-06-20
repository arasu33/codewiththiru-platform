package com.codewiththiru.notifications.config

data class NotificationPrivacyPolicy(
    val allowAnalyticsTracking: Boolean = true,
    val allowCrashlytics: Boolean = true,
    val retainHistoryDays: Int = 30,
    val collectDeviceMetrics: Boolean = false
)
