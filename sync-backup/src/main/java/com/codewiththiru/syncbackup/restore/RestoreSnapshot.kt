package com.codewiththiru.syncbackup.restore

import com.codewiththiru.syncbackup.backup.BackupSnapshot
import kotlinx.serialization.Serializable

@Serializable
data class RestoreSnapshot(
    val backupSnapshot: BackupSnapshot,
    val requiresMigration: Boolean
)
