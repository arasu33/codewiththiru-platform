package com.codewiththiru.billing.api

enum class BillingEnvironment {
    Debug,
    Internal,
    QA,
    Beta,
    Production,
    ;

    val isTestEnvironment: Boolean
        get() = this != Production
}
