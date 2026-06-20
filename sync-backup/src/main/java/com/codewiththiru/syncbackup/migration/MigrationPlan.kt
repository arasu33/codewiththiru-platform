package com.codewiththiru.syncbackup.migration

import com.codewiththiru.syncbackup.api.SchemaVersion
import com.codewiththiru.syncbackup.api.SyncEntity

interface MigrationPlan {
    val fromVersion: SchemaVersion
    val toVersion: SchemaVersion
    fun migrate(entity: SyncEntity): SyncEntity
}
