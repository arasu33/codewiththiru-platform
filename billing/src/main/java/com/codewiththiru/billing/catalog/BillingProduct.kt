package com.codewiththiru.billing.catalog

sealed interface BillingProduct {
    val id: String
    val title: String
    val description: String
    val priceFormatted: String
    val currencyCode: String
    val priceMicros: Long
}
