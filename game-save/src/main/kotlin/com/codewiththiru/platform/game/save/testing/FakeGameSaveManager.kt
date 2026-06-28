package com.codewiththiru.platform.game.save.testing

import com.codewiththiru.platform.game.save.api.SaveRequest
import com.codewiththiru.platform.game.save.api.SaveResponse
import com.codewiththiru.platform.game.save.api.SaveSlot
import com.codewiththiru.platform.game.save.manager.GameSaveManager
import com.codewiththiru.platform.game.save.manager.SaveSystemStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * An in-memory fake for GameSaveManager, useful for unit tests.
 */
class FakeGameSaveManager<T> : GameSaveManager<T> {
    private val saves = mutableMapOf<String, SaveResponse.Success<T>>()
    private val _status = MutableStateFlow(SaveSystemStatus.IDLE)

    override val status: StateFlow<SaveSystemStatus> = _status.asStateFlow()

    override suspend fun save(request: SaveRequest<T>): SaveResponse<T> {
        _status.value = SaveSystemStatus.SAVING

        if (!request.overwrite && saves.containsKey(request.slot.id)) {
            _status.value = SaveSystemStatus.IDLE
            return SaveResponse.Failure(Exception("Slot already exists and overwrite is false"), request.slot)
        }

        val success = SaveResponse.Success(request.slot, request.metadata, request.state)
        saves[request.slot.id] = success

        _status.value = SaveSystemStatus.IDLE
        return success
    }

    override suspend fun load(slot: SaveSlot): SaveResponse<T> {
        _status.value = SaveSystemStatus.LOADING

        val save = saves[slot.id]

        _status.value = SaveSystemStatus.IDLE
        return if (save != null) {
            save
        } else {
            SaveResponse.Failure(Exception("Save not found"), slot)
        }
    }

    override suspend fun delete(slot: SaveSlot): Boolean {
        return saves.remove(slot.id) != null
    }

    override suspend fun listSaves(): List<SaveResponse<T>> {
        return saves.values.toList()
    }

    override suspend fun sync() {
        _status.value = SaveSystemStatus.SYNCING
        // Fake sync delay
        _status.value = SaveSystemStatus.IDLE
    }
}
