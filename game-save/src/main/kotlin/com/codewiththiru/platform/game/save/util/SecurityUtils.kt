package com.codewiththiru.platform.game.save.util

import com.codewiththiru.platform.game.save.api.ConflictResolutionStrategy
import com.codewiththiru.platform.game.save.api.SaveMetadata

/**
 * Generates checksums for payloads to detect corruption.
 */
interface ChecksumGenerator {
    fun generateChecksum(payload: ByteArray): String
}

/**
 * Validates saves against tampering or corruption.
 */
interface SaveValidator {
    /**
     * Returns true if the payload matches the checksum and is not corrupted.
     */
    fun validate(
        payload: ByteArray,
        metadata: SaveMetadata,
    ): Boolean
}

/**
 * Resolves conflicts between local and remote saves.
 */
interface ConflictResolver {
    /**
     * Determines which metadata should be kept based on the provided strategy.
     * Returns the winning metadata (local or remote), or null if a manual merge is required.
     */
    fun resolve(
        local: SaveMetadata,
        remote: SaveMetadata,
        strategy: ConflictResolutionStrategy,
    ): SaveMetadata?
}
