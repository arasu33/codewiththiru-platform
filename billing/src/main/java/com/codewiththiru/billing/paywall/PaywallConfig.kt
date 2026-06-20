package com.codewiththiru.billing.paywall

data class PaywallConfig(
    val title: String,
    val subtitle: String,
    val features: List<String>,
    val template: PaywallTemplate,
    val offerHighlighting: Boolean
)
