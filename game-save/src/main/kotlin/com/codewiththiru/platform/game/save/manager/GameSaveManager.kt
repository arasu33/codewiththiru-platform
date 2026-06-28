package com.codewiththiru.platform.game.save.manager

import com.codewiththiru.platform.game.save.api.SaveRequest
import com.codewiththiru.platform.game.save.api.SaveResponse
import com.codewiththiru.platform.game.save.api.SaveSlot
import kotlinx.coroutines.flow.StateFlow

/**
 * The core orchestrator for game saves.
 */
interface GameSaveManager<T> {
    /**
     * Observable state representing the status of the save system (e.g. IDLE, SAVING, SYNCING).
     */
    val status: StateFlow<SaveSystemStatus>

    /**
     * Executes a save request.
     */
    suspend fun save(request: SaveRequest<T>): SaveResponse<T>

    /**
     * Loads a save from the given slot.
     */
    suspend fun load(slot: SaveSlot): SaveResponse<T>

    /**
     * Deletes a save slot.
     */
    suspend fun delete(slot: SaveSlot): Boolean

    /**
     * Lists all available saves.
     */
    suspend fun listSaves(): List<SaveResponse<T>>

    /**
     * Syncs local saves with cloud (if configured).
     */
    suspend fun sync()
}

enum class SaveSystemStatus {
    IDLE,
    SAVING,
    LOADING,
    SYNCING,
    ERROR,
}
