package com.codewiththiru.billing.offers

interface OfferManager {
    suspend fun fetchAvailableOffers(): List<CampaignOffer>
    suspend fun applyCouponCode(code: String): Result<CampaignOffer>
}
