package com.codewiththiru.platform.game.save.snapshot

/**
 * Manages full state snapshots, generally used for "Undo" functionality or history tracking.
 */
interface SnapshotManager<T> {
    /**
     * Takes a snapshot of the current state.
     */
    fun takeSnapshot(state: T)

    /**
     * Reverts to the immediately preceding snapshot.
     */
    fun rollback(): T?

    /**
     * Checks if a rollback is possible.
     */
    fun canRollback(): Boolean

    /**
     * Clears all snapshots.
     */
    fun clearSnapshots()
}
