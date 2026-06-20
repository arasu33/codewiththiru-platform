package com.codewiththiru.billing.purchase

import android.app.Activity
import com.codewiththiru.billing.api.BillingResult
import kotlinx.coroutines.flow.StateFlow

interface PurchaseManager {
    val purchaseState: StateFlow<PurchaseState>
    
    suspend fun launchPurchaseFlow(activity: Activity, productId: String): BillingResult<Unit>
    suspend fun restorePurchases(): BillingResult<Unit>
}
