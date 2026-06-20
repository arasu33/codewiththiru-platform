package com.codewiththiru.billing.config

import com.codewiththiru.billing.api.BillingEnvironment

data class BillingConfig(
    val environment: BillingEnvironment = BillingEnvironment.Production,
    val retryPolicy: RetryPolicy = RetryPolicy(),
    val connectionTimeoutMs: Long = 10000L
)

data class RetryPolicy(
    val maxRetries: Int = 3,
    val backoffMultiplier: Float = 2.0f,
    val initialDelayMs: Long = 1000L
)
