package com.codewiththiru.security.fraud

data class FraudSignal(
    val type: String,
    val value: String,
    val timestamp: Long
)
