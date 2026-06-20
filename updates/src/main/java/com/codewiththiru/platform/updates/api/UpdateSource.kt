package com.codewiththiru.platform.updates.api

import android.app.Activity

/**
 * Abstraction for fetching and initiating updates.
 * Allows switching between Play Store, Custom APKs, Huawei AppGallery, etc.
 */
interface UpdateSource {
    /**
     * Checks if an update is available.
     */
    suspend fun checkForUpdate(): Result<UpdateAvailabilityInfo>

    /**
     * Initiates the update flow.
     *
     * @param activity The activity context required for UI-based updates (like Play Core dialogs).
     * @param availabilityInfo The info obtained from [checkForUpdate].
     * @param updateType Whether this is a Flexible or Immediate update.
     */
    suspend fun startUpdate(
        activity: Activity,
        availabilityInfo: UpdateAvailabilityInfo,
        updateType: UpdateType
    ): Result<Unit>
}
