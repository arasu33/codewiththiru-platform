package com.codewiththiru.billing.catalog

data class SubscriptionProduct(
    override val id: String,
    override val title: String,
    override val description: String,
    override val priceFormatted: String,
    override val currencyCode: String,
    override val priceMicros: Long,
    val billingPeriod: String, // e.g. "P1M", "P1Y"
    val freeTrialPeriod: String? = null,
    val basePlanId: String
) : BillingProduct
