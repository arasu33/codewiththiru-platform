package com.codewiththiru.notifications.campaign

interface CampaignScheduler {
    fun scheduleCampaign(campaign: Campaign)
    fun cancelCampaign(campaignId: String)
}
