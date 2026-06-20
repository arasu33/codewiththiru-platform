package com.codewiththiru.billing.purchase

sealed class PurchaseState {
    object Idle : PurchaseState()
    object Pending : PurchaseState()
    object Success : PurchaseState()
    object Cancelled : PurchaseState()
    data class Error(val message: String) : PurchaseState()
}
