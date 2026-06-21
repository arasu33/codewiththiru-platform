package com.codewiththiru.billing.api

sealed class BillingState {
    object Disconnected : BillingState()

    object Connecting : BillingState()

    object Connected : BillingState()

    data class Error(val error: BillingError) : BillingState()
}
