package com.codewiththiru.remoteconfig.cache

class CacheVersionRegistry {
    private val migrations = mutableListOf<CacheMigration>()

    fun addMigration(migration: CacheMigration) {
        migrations.add(migration)
        migrations.sortBy { it.fromVersion }
    }

    fun getMigrations(currentVersion: Int, targetVersion: Int): List<CacheMigration> {
        return migrations.filter { it.fromVersion >= currentVersion && it.toVersion <= targetVersion }
    }
}
