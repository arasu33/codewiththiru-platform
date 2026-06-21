package com.codewiththiru.notifications.api

import kotlinx.serialization.Serializable

@Serializable
data class NotificationPayload(
    val id: String,
    val title: String,
    val body: String,
    val deepLink: String? = null,
    val priority: NotificationPriority = NotificationPriority.DEFAULT,
    val category: NotificationCategory = NotificationCategory.GENERAL,
    val imageUrl: String? = null,
    val data: Map<String, String> = emptyMap(),
    val timestamp: Long = System.currentTimeMillis(),
    val isSilent: Boolean = false,
    val requiresConsent: Boolean = true,
)
