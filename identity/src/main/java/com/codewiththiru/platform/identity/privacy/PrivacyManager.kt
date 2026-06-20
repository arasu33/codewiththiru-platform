package com.codewiththiru.platform.identity.privacy

interface PrivacyManager {
    suspend fun getPrivacyPreferences(userId: String): PrivacyPreferences
    suspend fun updatePrivacyPreferences(userId: String, preferences: PrivacyPreferences)
}
