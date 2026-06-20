package com.codewiththiru.billing.paywall

interface PaywallAnalytics {
    fun logPaywallImpression(variantId: String)
    fun logPaywallDismissed(variantId: String)
    fun logPaywallConversion(variantId: String, productId: String)
}
