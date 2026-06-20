package com.codewiththiru.security.fraud

data class FraudPolicy(
    val blockThreshold: Double,
    val reviewThreshold: Double
)
