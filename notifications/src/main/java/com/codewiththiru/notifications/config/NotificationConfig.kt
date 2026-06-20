package com.codewiththiru.notifications.config

data class NotificationConfig(
    val environment: NotificationEnvironment = NotificationEnvironment.PRODUCTION,
    val defaultChannelId: String = "general",
    val smallIconResId: Int,
    val colorResId: Int? = null,
    val enableAnalytics: Boolean = true,
    val quietHoursPolicy: QuietHoursPolicy = QuietHoursPolicy()
)
