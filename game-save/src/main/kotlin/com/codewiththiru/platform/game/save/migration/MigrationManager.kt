package com.codewiththiru.platform.game.save.migration

import com.codewiththiru.platform.game.save.api.SaveMetadata

/**
 * Handles upgrading older save schemas to the current version.
 */
interface MigrationManager<T> {
    /**
     * Migrates a loaded payload if its schema version is older than the current expected version.
     */
    fun migrate(
        payload: ByteArray,
        metadata: SaveMetadata,
        currentVersion: Int,
    ): ByteArray
}

/**
 * Resolves version compatibility.
 */
interface VersionResolver {
    /**
     * Checks if the given metadata version can be migrated to the current version.
     * Returns true if it's forward or backward compatible.
     */
    fun isCompatible(
        metadata: SaveMetadata,
        currentVersion: Int,
    ): Boolean
}
