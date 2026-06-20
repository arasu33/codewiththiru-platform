package com.codewiththiru.syncbackup.api

data class SyncConfig(
    val environment: SyncEnvironment = SyncEnvironment.PRODUCTION,
    val isAutoSyncEnabled: Boolean = true,
    val isBackupEnabled: Boolean = true,
    val maxRetries: Int = 3
)

enum class SyncEnvironment {
    DEVELOPMENT,
    STAGING,
    PRODUCTION
}
