package com.codewiththiru.platform.updates.api

/**
 * Abstraction for verifying app integrity (e.g. Play Integrity API)
 * before executing a force update.
 */
interface UpdateIntegrityProvider {
    /**
     * Verifies the app's integrity.
     * @return true if the environment is trusted and secure.
     */
    suspend fun verify(): Boolean
}
