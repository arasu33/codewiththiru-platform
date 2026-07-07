package com.codewiththiru.platform.game.sync.api

import kotlinx.serialization.Serializable

@Serializable
enum class SyncType {
    INCREMENTAL,
    FULL,
    DELTA,
    PUSH,
    PULL,
}

@Serializable
enum class SyncStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    FAILED,
    CONFLICT,
}

sealed class SyncEvent {
    data class SyncStarted(
        val type: SyncType,
    ) : SyncEvent()

    data class SyncCompleted(
        val type: SyncType,
        val durationMs: Long,
    ) : SyncEvent()

    data class SyncFailed(
        val type: SyncType,
        val reason: String,
    ) : SyncEvent()

    data class ConflictDetected(
        val collection: String,
    ) : SyncEvent()

    data class ConflictResolved(
        val collection: String,
        val resolutionStrategy: String,
    ) : SyncEvent()
}
