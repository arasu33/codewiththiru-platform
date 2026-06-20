package com.codewiththiru.syncbackup.recovery

data class RecoveryPolicy(
    val maxCheckpointsStored: Int = 5,
    val autoCheckpointBeforeSync: Boolean = true,
    val autoCheckpointBeforeRestore: Boolean = true
)
