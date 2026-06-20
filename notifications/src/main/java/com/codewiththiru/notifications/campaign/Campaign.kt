package com.codewiththiru.notifications.campaign

import com.codewiththiru.notifications.api.NotificationPayload

data class Campaign(
    val id: String,
    val name: String,
    val audience: CampaignAudience,
    val payloads: List<NotificationPayload>,
    val priority: CampaignPriority = CampaignPriority.NORMAL,
    val startDateMillis: Long,
    val endDateMillis: Long
)
