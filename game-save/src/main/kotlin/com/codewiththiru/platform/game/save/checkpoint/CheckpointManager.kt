package com.codewiththiru.platform.game.save.checkpoint

/**
 * Manages rapid, temporary save points during an active session (e.g. at the start of a level).
 */
interface CheckpointManager<T> {
    /**
     * Creates a new checkpoint for the given state.
     */
    suspend fun createCheckpoint(state: T): String

    /**
     * Restores the state from the most recent checkpoint.
     */
    suspend fun restoreLatestCheckpoint(): T?

    /**
     * Restores the state from a specific checkpoint ID.
     */
    suspend fun restoreCheckpoint(id: String): T?

    /**
     * Clears all checkpoints (usually when a level/session ends).
     */
    suspend fun clearCheckpoints()
}
