package com.codewiththiru.billing.offers

data class OfferEligibility(
    val isEligible: Boolean,
    val reason: String? = null
)
