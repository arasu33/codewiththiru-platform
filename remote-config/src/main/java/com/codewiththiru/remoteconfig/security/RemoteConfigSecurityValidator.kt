package com.codewiththiru.remoteconfig.security

interface RemoteConfigSecurityValidator {
    /**
     * Validates if a downloaded payload matches expected schema, types, and required keys.
     */
    fun validatePayload(payload: Map<String, Any>): Boolean

    /**
     * Validates SHA256 checksum and signatures.
     */
    fun validateSignature(payload: String, expectedHash: String): Boolean
    
    /**
     * Replay protection & Timestamp validation
     */
    fun validateTimestamp(timestamp: Long): Boolean
}

class DefaultRemoteConfigSecurityValidator : RemoteConfigSecurityValidator {
    override fun validatePayload(payload: Map<String, Any>): Boolean {
        // Implement schema validation, required keys validation
        return true
    }

    override fun validateSignature(payload: String, expectedHash: String): Boolean {
        // Implement signature & SHA256 check
        return true
    }

    override fun validateTimestamp(timestamp: Long): Boolean {
        // Implement replay protection
        val currentTime = System.currentTimeMillis()
        // Reject if timestamp is in the future or older than max allowed cache age (e.g., 24 hrs)
        return timestamp <= currentTime
    }
}
