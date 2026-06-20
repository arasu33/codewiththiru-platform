package com.codewiththiru.billing.entitlement

sealed class FeatureGate {
    abstract val key: String

    data class Subscribed(override val key: String) : FeatureGate()
    data class OneTimePurchase(override val key: String) : FeatureGate()
    data class Free(override val key: String) : FeatureGate()
}
