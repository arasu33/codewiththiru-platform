package com.codewiththiru.platform.identity.privacy

interface ConsentManager {
    suspend fun recordConsent(
        userId: String,
        policyId: String,
        granted: Boolean,
    )

    suspend fun hasConsented(
        userId: String,
        policyId: String,
    ): Boolean

    suspend fun revokeConsent(
        userId: String,
        policyId: String,
    )
}
