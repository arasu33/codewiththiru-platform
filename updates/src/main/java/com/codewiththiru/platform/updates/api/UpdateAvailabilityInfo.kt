package com.codewiththiru.platform.updates.api

/**
 * Represents the availability of an update from a source (Play Store, Custom API).
 */
data class UpdateAvailabilityInfo(
    val isUpdateAvailable: Boolean,
    val availableVersionCode: Int,
    val clientStalenessDays: Int?,
    val isFlexibleAllowed: Boolean,
    val isImmediateAllowed: Boolean,
    /**
     * Opaque payload required by the underlying update mechanism (e.g. AppUpdateInfo).
     */
    val rawPayload: Any? = null
)
