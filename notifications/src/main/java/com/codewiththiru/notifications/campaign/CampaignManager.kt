package com.codewiththiru.notifications.campaign

import com.codewiththiru.notifications.api.NotificationPayload

interface CampaignManager {
    fun registerCampaign(campaign: Campaign)
    fun processCampaigns()
    fun stopCampaign(campaignId: String)
    fun getActiveCampaigns(): List<Campaign>
}
