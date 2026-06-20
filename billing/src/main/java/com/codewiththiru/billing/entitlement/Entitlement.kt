package com.codewiththiru.billing.entitlement

data class Entitlement(
    val id: String,
    val isOwned: Boolean,
    val sourceProductId: String?,
    val expirationDateMillis: Long? = null
)
