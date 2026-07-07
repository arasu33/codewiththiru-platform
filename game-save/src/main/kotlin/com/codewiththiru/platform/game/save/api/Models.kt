package com.codewiththiru.platform.game.save.api

import kotlinx.serialization.Serializable

/**
 * Types of game saves supported by the framework.
 */
enum class SaveType {
    MANUAL,
    AUTO,
    QUICK,
    CHECKPOINT,
    SUSPEND,
    CRASH_RECOVERY,
    CLOUD,
    TEMPORARY,
}

/**
 * Represents a specific slot or identifier for a save.
 */
@Serializable
data class SaveSlot(
    val id: String,
    val type: SaveType,
    val isReadOnly: Boolean = false,
) {
    companion object {
        fun auto(id: String = "auto") = SaveSlot(id, SaveType.AUTO)

        fun quick(id: String = "quick") = SaveSlot(id, SaveType.QUICK)

        fun suspend(id: String = "suspend") = SaveSlot(id, SaveType.SUSPEND)

        fun manual(id: String) = SaveSlot(id, SaveType.MANUAL)
    }
}

/**
 * Metadata associated with a save payload.
 */
@Serializable
data class SaveMetadata(
    val timestampMs: Long,
    val schemaVersion: Int,
    val playtimeSeconds: Long,
    val gameId: String,
    val gameVersion: String,
    val checksum: String? = null,
    val extraData: Map<String, String> = emptyMap(),
)

/**
 * Request wrapper for save operations.
 */
data class SaveRequest<T>(
    val slot: SaveSlot,
    val state: T,
    val metadata: SaveMetadata,
    val overwrite: Boolean = true,
)

/**
 * Response wrapper for save operations.
 */
sealed class SaveResponse<out T> {
    data class Success<T>(
        val slot: SaveSlot,
        val metadata: SaveMetadata,
        val state: T?,
    ) : SaveResponse<T>()

    data class Failure(
        val error: Throwable,
        val slot: SaveSlot,
    ) : SaveResponse<Nothing>()
}

/**
 * Policy governing save behaviors like autosave limits.
 */
data class SavePolicy(
    val maxAutoSaves: Int = 3,
    val maxQuickSaves: Int = 1,
    val conflictResolution: ConflictResolutionStrategy = ConflictResolutionStrategy.NEWEST_WINS,
)

enum class ConflictResolutionStrategy {
    NEWEST_WINS,
    OLDEST_WINS,
    MANUAL,
    MERGE,
}
