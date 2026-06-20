package com.codewiththiru.platform.growth.referral

data class ReferralCode(
    val code: String,
    val ownerId: String,
    val maxUses: Int? = null,
    val currentUses: Int = 0
)
