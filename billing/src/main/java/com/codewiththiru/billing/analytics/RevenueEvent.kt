package com.codewiththiru.billing.analytics

sealed class RevenueEvent {
    data class Purchase(val productId: String, val amountMicros: Long, val currencyCode: String) : RevenueEvent()
    data class SubscriptionRenewal(val productId: String, val amountMicros: Long, val currencyCode: String) : RevenueEvent()
    data class Refund(val productId: String, val amountMicros: Long, val currencyCode: String) : RevenueEvent()
}
