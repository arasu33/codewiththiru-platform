package com.codewiththiru.billing.catalog

interface CatalogMapper<T> {
    fun mapToProduct(rawProduct: T): BillingProduct
}
