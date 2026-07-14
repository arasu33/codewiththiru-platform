package com.codewiththiru.platform.updates.internal

import android.app.Activity
import com.codewiththiru.platform.updates.api.UpdateAvailabilityInfo
import com.codewiththiru.platform.updates.api.UpdateSource
import com.codewiththiru.platform.updates.api.UpdateType
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.requestAppUpdateInfo

class PlayStoreUpdateSource(
    private val appUpdateManager: AppUpdateManager,
) : UpdateSource {
    override suspend fun checkForUpdate(): Result<UpdateAvailabilityInfo> =
        try {
            val appUpdateInfo: AppUpdateInfo = appUpdateManager.requestAppUpdateInfo()
            val isAvailable = appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isFlexibleAllowed = appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            val isImmediateAllowed = appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)

            Result.success(
                UpdateAvailabilityInfo(
                    isUpdateAvailable = isAvailable,
                    availableVersionCode = appUpdateInfo.availableVersionCode(),
                    clientStalenessDays = appUpdateInfo.clientVersionStalenessDays(),
                    isFlexibleAllowed = isFlexibleAllowed,
                    isImmediateAllowed = isImmediateAllowed,
                    rawPayload = appUpdateInfo,
                ),
            )
        } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException ||
                e is kotlin.coroutines.cancellation.CancellationException
            ) {
                throw e
            }
            Result.failure(e)
        }

    @Suppress("ReturnCount")
    override suspend fun startUpdate(
        activity: Activity,
        availabilityInfo: UpdateAvailabilityInfo,
        updateType: UpdateType,
    ): Result<Unit> {
        return try {
            val appUpdateInfo =
                availabilityInfo.rawPayload as? AppUpdateInfo
                    ?: return Result.failure(IllegalStateException("Missing AppUpdateInfo payload"))

            val playCoreType =
                when (updateType) {
                    UpdateType.Flexible -> AppUpdateType.FLEXIBLE
                    UpdateType.Immediate, UpdateType.Force -> AppUpdateType.IMMEDIATE
                    UpdateType.WhatsNewOnly -> return Result.success(Unit) // Does not apply here
                }

            val started =
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    playCoreType,
                    activity,
                    PLAY_CORE_UPDATE_REQUEST_CODE,
                )

            if (started) {
                Result.success(Unit)
            } else {
                Result.failure(RuntimeException("Failed to start Play Core update flow"))
            }
        } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException ||
                e is kotlin.coroutines.cancellation.CancellationException
            ) {
                throw e
            }
            Result.failure(e)
        }
    }

    companion object {
        const val PLAY_CORE_UPDATE_REQUEST_CODE = 4531
    }
}
