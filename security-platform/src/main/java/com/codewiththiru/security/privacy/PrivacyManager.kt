package com.codewiththiru.security.privacy

interface PrivacyManager {
    fun hasConsent(feature: String): Boolean
    fun requestDataDeletion(userId: String)
}
