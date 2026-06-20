package com.codewiththiru.billing.entitlement

class EntitlementCache {
    private val cache = mutableMapOf<String, Entitlement>()

    fun get(id: String): Entitlement? = cache[id]
    
    fun put(entitlement: Entitlement) {
        cache[entitlement.id] = entitlement
    }
    
    fun clear() = cache.clear()
}
