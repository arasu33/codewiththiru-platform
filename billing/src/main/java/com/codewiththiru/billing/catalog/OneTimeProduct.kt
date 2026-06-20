package com.codewiththiru.billing.catalog

data class OneTimeProduct(
    override val id: String,
    override val title: String,
    override val description: String,
    override val priceFormatted: String,
    override val currencyCode: String,
    override val priceMicros: Long
) : BillingProduct
