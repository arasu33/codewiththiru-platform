package com.codewiththiru.syncbackup.recovery

data class RecoveryCheckpoint(
    val id: String,
    val timestampMillis: Long,
    val reason: String,
    val isValidated: Boolean
)
