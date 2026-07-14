package com.codewiththiru.platform.game.save

import com.codewiththiru.platform.core.result.CustResult

/**
 * Migration step from one save state version to another.
 */
interface MigrationStep {
    val fromVersion: Int
    val toVersion: Int

    fun migrate(data: Map<String, String>): Map<String, String>
}

/**
 * Manages migration of older serialized game save versions to the latest format.
 */
class VersionMigration(
    private val steps: List<MigrationStep>,
) {
    fun migrate(
        data: Map<String, String>,
        currentVersion: Int,
        targetVersion: Int,
    ): Map<String, String> {
        var migratedData = data
        var tempVersion = currentVersion
        while (tempVersion < targetVersion) {
            val step = steps.find { it.fromVersion == tempVersion } ?: break
            migratedData = step.migrate(migratedData)
            tempVersion = step.toVersion
        }
        return migratedData
    }
}

/**
 * Interface for backup operations (e.g. cloud sync or local directory copying).
 */
interface BackupManager {
    suspend fun backupSave(
        saveId: String,
        content: String,
    ): CustResult<Unit>

    suspend fun restoreSave(saveId: String): CustResult<String>
}

/**
 * No-op fallback implementation of BackupManager.
 */
class LocalBackupManager : BackupManager {
    private val store = java.util.concurrent.ConcurrentHashMap<String, String>()

    override suspend fun backupSave(
        saveId: String,
        content: String,
    ): CustResult<Unit> {
        store[saveId] = content
        return CustResult.Success(Unit)
    }

    override suspend fun restoreSave(saveId: String): CustResult<String> {
        val data = store[saveId]
        return if (data != null) {
            CustResult.Success(data)
        } else {
            CustResult.Failure(Exception("Backup not found for ID: $saveId"))
        }
    }
}
