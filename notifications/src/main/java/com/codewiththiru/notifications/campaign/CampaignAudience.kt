package com.codewiththiru.notifications.campaign

data class CampaignAudience(
    val segmentIds: List<String>,
    val excludeSegmentIds: List<String> = emptyList()
)
