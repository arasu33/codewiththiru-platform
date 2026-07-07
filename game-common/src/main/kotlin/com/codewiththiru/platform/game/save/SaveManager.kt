package com.codewiththiru.platform.game.save

import com.codewiththiru.platform.game.engine.GameState
import kotlin.reflect.typeOf

interface GameStorage {
    fun getString(
        key: String,
        defaultValue: String? = null,
    ): String?

    fun putString(
        key: String,
        value: String,
    )

    fun remove(key: String)

    fun hasKey(key: String): Boolean
}

class InMemoryGameStorage : GameStorage {
    private val map = java.util.concurrent.ConcurrentHashMap<String, String>()

    override fun getString(
        key: String,
        defaultValue: String?,
    ): String? = map[key] ?: defaultValue

    override fun putString(
        key: String,
        value: String,
    ) {
        map[key] = value
    }

    override fun remove(key: String) {
        map.remove(key)
    }

    override fun hasKey(key: String): Boolean = map.containsKey(key)
}

@Deprecated("Use GameSaveManager from :game-save module instead")
interface SaveManager {
    fun save(
        saveId: String,
        state: GameState,
    )

    fun load(saveId: String): GameState?

    fun delete(saveId: String)

    fun autoSave(state: GameState)

    fun quickSave(state: GameState)

    fun hasResumeState(): Boolean

    fun getResumeState(): GameState?

    fun clearResumeState()
}

class DefaultSaveManager(
    private val storage: GameStorage,
    private val serializer: StateSerializer,
) : SaveManager {
    private val resumeKey = "game_resume_state"
    private val autosaveKey = "game_autosave_state"
    private val quicksaveKey = "game_quicksave_state"

    override fun save(
        saveId: String,
        state: GameState,
    ) {
        val serialized = serializer.serialize(state, typeOf<GameState>())
        storage.putString("save_$saveId", serialized)
    }

    override fun load(saveId: String): GameState? {
        val serialized = storage.getString("save_$saveId") ?: return null
        return try {
            serializer.deserialize(serialized, typeOf<GameState>())
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            null
        }
    }

    override fun delete(saveId: String) {
        storage.remove("save_$saveId")
    }

    override fun autoSave(state: GameState) {
        val serialized = serializer.serialize(state, typeOf<GameState>())
        storage.putString(autosaveKey, serialized)
        storage.putString(resumeKey, serialized)
    }

    override fun quickSave(state: GameState) {
        val serialized = serializer.serialize(state, typeOf<GameState>())
        storage.putString(quicksaveKey, serialized)
        storage.putString(resumeKey, serialized)
    }

    override fun hasResumeState(): Boolean = storage.hasKey(resumeKey)

    override fun getResumeState(): GameState? {
        val serialized = storage.getString(resumeKey) ?: return null
        return try {
            serializer.deserialize(serialized, typeOf<GameState>())
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            null
        }
    }

    override fun clearResumeState() {
        storage.remove(resumeKey)
        storage.remove(autosaveKey)
        storage.remove(quicksaveKey)
    }
}
