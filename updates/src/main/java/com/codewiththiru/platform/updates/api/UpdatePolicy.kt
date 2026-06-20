package com.codewiththiru.platform.updates.api

/**
 * Evaluates whether an update should be requested based on configuration,
 * current availability, and user cooldowns.
 */
interface UpdatePolicy {
    /**
     * Evaluates the update eligibility.
     *
     * @param currentVersion The current installed version of the app.
     * @param availableVersion The available version to update to.
     * @param isForceUpdateRequired Whether remote config dictates this is a mandatory update.
     * @param clientStalenessDays How many days the update has been available on the store.
     * @return [UpdateEligibilityResult] describing whether an update should be prompted.
     */
    suspend fun evaluate(
        currentVersion: Int,
        availableVersion: Int,
        isForceUpdateRequired: Boolean,
        clientStalenessDays: Int? = null
    ): UpdateEligibilityResult
}
