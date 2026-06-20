package com.codewiththiru.syncbackup.conflict

import com.codewiththiru.syncbackup.api.SyncEntity

data class ConflictPolicy(
    val defaultStrategy: MergeStrategy = MergeStrategy.LAST_WRITE_WINS,
    val collectionStrategies: Map<String, MergeStrategy> = emptyMap()
)

enum class MergeStrategy {
    LAST_WRITE_WINS,
    SERVER_WINS,
    CLIENT_WINS,
    CUSTOM_MERGE
}

sealed class ConflictResult {
    data class Resolved(val winner: SyncEntity) : ConflictResult()
    data class Unresolved(val localEntity: SyncEntity, val remoteEntity: SyncEntity) : ConflictResult()
}
