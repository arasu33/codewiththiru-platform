package com.codewiththiru.syncbackup.restore

data class RestorePolicy(
    val strictSchemaValidation: Boolean = true,
    val allowDowngrade: Boolean = false,
    val mergeConflicts: Boolean = false
)
