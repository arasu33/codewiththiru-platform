package com.codewiththiru.platform.updates.api

import android.app.Activity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Central manager for update flows.
 */
class UpdateManager(
    private val updateSource: UpdateSource,
    private val updatePolicy: UpdatePolicy,
    private val storage: UpdateStorageProvider,
    private val clock: UpdateClock,
) {
    private val _updateState = MutableStateFlow<UpdateResult?>(null)
    val updateState: Flow<UpdateResult?> = _updateState.asStateFlow()

    @Suppress("ReturnCount")
    suspend fun checkAndPrompt(
        activity: Activity,
        currentVersionCode: Int,
    ): UpdateEffect {
        val availabilityResult = updateSource.checkForUpdate()
        if (availabilityResult.isFailure) {
            return UpdateEffect.ShowError("Failed to check for updates")
        }

        val info = availabilityResult.getOrNull() ?: return UpdateEffect.ShowError("No info")

        val eligibility =
            updatePolicy.evaluate(
                currentVersion = currentVersionCode,
                availableVersion = info.availableVersionCode,
                isForceUpdateRequired = false, // Could be injected from remote config
                clientStalenessDays = info.clientStalenessDays,
            )

        return when (eligibility) {
            UpdateEligibilityResult.ForceUpdateRequired -> {
                if (info.isImmediateAllowed) {
                    updateSource.startUpdate(activity, info, UpdateType.Force)
                }
                UpdateEffect.ShowForceUpdate
            }
            UpdateEligibilityResult.Eligible -> {
                storage.setLastPromptDate(clock.now())
                if (info.isFlexibleAllowed) {
                    updateSource.startUpdate(activity, info, UpdateType.Flexible)
                    UpdateEffect.LaunchFlexibleUpdate
                } else if (info.isImmediateAllowed) {
                    updateSource.startUpdate(activity, info, UpdateType.Immediate)
                    UpdateEffect.LaunchImmediateUpdate
                } else {
                    UpdateEffect.ShowError("No valid update type allowed")
                }
            }
            UpdateEligibilityResult.CooldownActive,
            UpdateEligibilityResult.AlreadyShown,
            -> {
                // Determine if we should show What's New instead
                val lastShownNotes = storage.getLastShownReleaseNotesVersion()
                if (lastShownNotes < currentVersionCode) {
                    storage.setLastShownReleaseNotesVersion(currentVersionCode)
                    UpdateEffect.ShowWhatsNew
                } else {
                    UpdateEffect.ShowError("Update deferred or cooldown active") // Or a NO-OP effect
                }
            }
            UpdateEligibilityResult.NoUpdateAvailable -> {
                val lastShownNotes = storage.getLastShownReleaseNotesVersion()
                if (lastShownNotes < currentVersionCode) {
                    storage.setLastShownReleaseNotesVersion(currentVersionCode)
                    UpdateEffect.ShowWhatsNew
                } else {
                    UpdateEffect.ShowError("App is up to date") // NO-OP
                }
            }
        }
    }

    suspend fun recordUserDeferral() {
        storage.setLastDeferDate(clock.now())
        _updateState.value = UpdateResult.Deferred
    }
}
