package com.codewiththiru.billing.analytics

interface RevenueTracker {
    fun trackPurchase(productId: String, amountMicros: Long, currencyCode: String)
    fun trackRefund(productId: String, amountMicros: Long, currencyCode: String)
}
