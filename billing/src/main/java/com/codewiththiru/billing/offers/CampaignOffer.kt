package com.codewiththiru.billing.offers

data class CampaignOffer(
    val offerId: String,
    val description: String,
    val discountPercentage: Int?,
    val expirationDateMillis: Long?
)
