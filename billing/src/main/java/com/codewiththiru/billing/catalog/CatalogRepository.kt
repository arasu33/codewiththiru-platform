package com.codewiththiru.billing.catalog

interface CatalogRepository {
    suspend fun fetchProducts(productIds: List<String>): Result<List<BillingProduct>>
}
