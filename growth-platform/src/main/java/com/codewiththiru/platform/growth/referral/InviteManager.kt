package com.codewiththiru.platform.growth.referral

interface InviteManager {
    suspend fun generateInviteLink(userId: String, campaignId: String): InviteLink
    suspend fun trackInviteClick(linkId: String)
}

class DefaultInviteManager(
    private val baseInviteUrl: String = "https://cwthiru.com/invite/",
) : InviteManager {
    override suspend fun generateInviteLink(userId: String, campaignId: String): InviteLink {
        val sanitizedBase = if (baseInviteUrl.endsWith("/")) baseInviteUrl else "$baseInviteUrl/"
        return InviteLink("$sanitizedBase$userId", campaignId, userId)
    }

    override suspend fun trackInviteClick(linkId: String) {
        // Track the click locally or via analytics
    }
}
