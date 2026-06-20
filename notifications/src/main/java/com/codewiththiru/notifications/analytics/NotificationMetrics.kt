package com.codewiththiru.notifications.analytics

data class NotificationMetrics(
    val totalDelivered: Int,
    val totalOpened: Int,
    val totalDismissed: Int,
    val openRate: Float,
    val conversionRate: Float
)
