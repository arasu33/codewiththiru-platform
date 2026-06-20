package com.codewiththiru.platform.growth.referral

interface ReferralManager {
    suspend fun createReferralCode(userId: String): ReferralCode
    suspend fun applyReferralCode(code: String): Boolean
    suspend fun getActiveCampaign(): ReferralCampaign?
}

class DefaultReferralManager : ReferralManager {
    override suspend fun createReferralCode(userId: String): ReferralCode {
        return ReferralCode("REF-${userId.take(5).uppercase()}", userId)
    }

    override suspend fun applyReferralCode(code: String): Boolean {
        // Validation logic
        return true
    }

    override suspend fun getActiveCampaign(): ReferralCampaign? {
        return ReferralCampaign("summer_promo", 10.0, true)
    }
}
