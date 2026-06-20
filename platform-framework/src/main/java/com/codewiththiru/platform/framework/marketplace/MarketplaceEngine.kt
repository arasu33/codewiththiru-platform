package com.codewiththiru.platform.framework.marketplace

data class MarketplaceEntry(
    val entryId: String,
    val name: String,
    val description: String,
    val version: String,
    val type: EntryType
) {
    enum class EntryType {
        TEMPLATE, MODULE, FEATURE
    }
}

data class MarketplaceCatalog(
    val entries: List<MarketplaceEntry>
)

interface MarketplaceManager {
    fun fetchCatalog(): MarketplaceCatalog
    fun installEntry(entryId: String)
}
