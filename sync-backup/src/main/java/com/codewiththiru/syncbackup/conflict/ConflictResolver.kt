package com.codewiththiru.syncbackup.conflict

import com.codewiththiru.syncbackup.api.SyncEntity

interface ConflictResolver {
    fun resolve(localEntity: SyncEntity, remoteEntity: SyncEntity, policy: ConflictPolicy): ConflictResult
}
