package com.codewiththiru.billing.analytics

interface BillingAnalyticsProvider {
    fun logEvent(event: RevenueEvent)
}
