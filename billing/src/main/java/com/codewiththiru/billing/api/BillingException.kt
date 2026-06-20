package com.codewiththiru.billing.api

class BillingException(
    val error: BillingError,
    cause: Throwable? = null
) : Exception("Billing Error [${error.code}]: ${error.message}", cause)
