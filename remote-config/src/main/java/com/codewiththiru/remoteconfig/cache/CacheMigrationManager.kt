package com.codewiththiru.remoteconfig.cache

import android.util.Log

class CacheMigrationManager(
    private val registry: CacheVersionRegistry,
    private val metrics: CacheMetrics
) {
    suspend fun migrateIfNeeded(currentConfig: CachedRemoteConfig, targetVersion: Int): CachedRemoteConfig {
        if (currentConfig.schemaVersion == targetVersion) {
            return currentConfig
        }

        if (currentConfig.schemaVersion > targetVersion) {
            Log.w("CacheMigrationManager", "Downgrade requested. Wiping cache.")
            metrics.recordCorruption()
            return CachedRemoteConfig(targetVersion, System.currentTimeMillis(), "{}")
        }

        var migratedPayload = currentConfig.payload
        var currentVersion = currentConfig.schemaVersion

        val path = registry.getMigrations(currentVersion, targetVersion)
        
        try {
            for (migration in path) {
                if (migration.fromVersion != currentVersion) {
                    throw IllegalStateException("Missing migration step from $currentVersion")
                }
                migratedPayload = migration.migrate(migratedPayload)
                currentVersion = migration.toVersion
                metrics.recordMigration()
            }
            
            if (currentVersion != targetVersion) {
                throw IllegalStateException("Failed to reach target version $targetVersion")
            }
        } catch (e: Exception) {
            Log.e("CacheMigrationManager", "Migration failed. Wiping cache.", e)
            metrics.recordCorruption()
            return CachedRemoteConfig(targetVersion, System.currentTimeMillis(), "{}")
        }

        return CachedRemoteConfig(targetVersion, System.currentTimeMillis(), migratedPayload)
    }
}
