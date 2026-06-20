package com.codewiththiru.billing.catalog

import kotlinx.coroutines.flow.StateFlow

interface ProductCatalog {
    val products: StateFlow<List<BillingProduct>>
    
    suspend fun refreshCatalog(): Result<Unit>
    fun getProduct(id: String): BillingProduct?
}
