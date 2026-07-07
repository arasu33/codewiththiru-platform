package com.codewiththiru.platform.updates.api

/**
 * Represents the configuration for the update module, usually fetched from Remote Config.
 */
data class UpdateConfig(
    val minRequiredVersionCode: Int = 0,
    val latestVersionCode: Int = 0,
    val flexibleUpdateCooldownDays: Int = 3,
    val isForceUpdateEnabled: Boolean = false,
)
