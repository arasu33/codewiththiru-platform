package com.codewiththiru.notifications.campaign

enum class CampaignPriority(val weight: Int) {
    LOW(1),
    NORMAL(2),
    HIGH(3),
    CRITICAL(4)
}
