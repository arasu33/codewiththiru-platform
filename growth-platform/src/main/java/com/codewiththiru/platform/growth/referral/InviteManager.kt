package com.codewiththiru.platform.growth.referral

interface InviteManager {
    suspend fun generateInviteLink(userId: String, campaignId: String): InviteLink
    suspend fun trackInviteClick(linkId: String)
}

class DefaultInviteManager : InviteManager {
    override suspend fun generateInviteLink(userId: String, campaignId: String): InviteLink {
        return InviteLink("https://cwthiru.com/invite/$userId", campaignId, userId)
    }

    override suspend fun trackInviteClick(linkId: String) {
        // Track the click locally or via analytics
    }
}
