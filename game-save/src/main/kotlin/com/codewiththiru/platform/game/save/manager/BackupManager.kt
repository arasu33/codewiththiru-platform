package com.codewiththiru.platform.game.save.manager

import com.codewiththiru.platform.game.save.api.SaveResponse
import com.codewiththiru.platform.game.save.api.SaveSlot

/**
 * Manages backup copies of save slots to prevent corruption or accidental loss.
 */
interface BackupManager<T> {
    /**
     * Creates a backup of the specified save slot.
     */
    suspend fun backup(slot: SaveSlot): Boolean

    /**
     * Lists all available backups for a save slot.
     */
    suspend fun listBackups(slot: SaveSlot): List<String>
}

/**
 * Manages restoring saves from backups or previous versions.
 */
interface RestoreManager<T> {
    /**
     * Restores a save slot from a specific backup ID.
     */
    suspend fun restore(
        slot: SaveSlot,
        backupId: String,
    ): SaveResponse<T>
}
