package com.codewiththiru.platform.analytics.domain.validation

import java.security.MessageDigest

/**
 * Utility to hash sensitive user identifiers before transmitting to analytics platforms.
 */
public object AnalyticsUserHasher {

    /**
     * Hashes the given [userId] using SHA-256.
     * Returns a truncated hash for reporting use cases.
     */
    public fun hash(userId: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(userId.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
