package com.codewiththiru.platform.updates.internal

import com.codewiththiru.platform.updates.api.UpdateClock
import com.codewiththiru.platform.updates.api.UpdateConfig
import com.codewiththiru.platform.updates.api.UpdateEligibilityResult
import com.codewiththiru.platform.updates.api.UpdatePolicy
import com.codewiththiru.platform.updates.api.UpdateStorageProvider
import java.util.concurrent.TimeUnit

class DefaultUpdatePolicy(
    private val config: UpdateConfig,
    private val storage: UpdateStorageProvider,
    private val clock: UpdateClock,
) : UpdatePolicy {
    override suspend fun evaluate(
        currentVersion: Int,
        availableVersion: Int,
        isForceUpdateRequired: Boolean,
        clientStalenessDays: Int?,
    ): UpdateEligibilityResult {
        if (availableVersion <= currentVersion) {
            return UpdateEligibilityResult.NoUpdateAvailable
        }

        if (isForceUpdateRequired || currentVersion < config.minRequiredVersionCode) {
            return UpdateEligibilityResult.ForceUpdateRequired
        }

        val lastDeferDate = storage.getLastDeferDate()
        val cooldownMillis = TimeUnit.DAYS.toMillis(config.flexibleUpdateCooldownDays.toLong())

        val isCooldownActive = clock.now() - lastDeferDate < cooldownMillis
        if (isCooldownActive) {
            return UpdateEligibilityResult.CooldownActive
        }

        val lastPromptDate = storage.getLastPromptDate()
        val promptCooldownMillis = TimeUnit.DAYS.toMillis(1) // Don't prompt more than once a day
        if (clock.now() - lastPromptDate < promptCooldownMillis) {
            return UpdateEligibilityResult.AlreadyShown
        }

        return UpdateEligibilityResult.Eligible
    }
}
