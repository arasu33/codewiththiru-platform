package com.codewiththiru.billing.entitlement

class OfflineEntitlementStore {
    // SharedPreferences or DataStore to hold offline cached entitlements
    fun saveEntitlements(entitlements: List<Entitlement>) {}
    fun loadEntitlements(): List<Entitlement> = emptyList()
}
