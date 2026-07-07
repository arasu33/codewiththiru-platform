package com.codewiththiru.platform.rating.policy

import com.codewiththiru.platform.rating.model.RatingEligibilityResult
import com.codewiththiru.platform.rating.repository.CustClock
import com.codewiththiru.platform.rating.repository.RatingRepository
import java.util.concurrent.TimeUnit

/**
 * Evaluates storage metrics against rules and policies to determine rating prompt eligibility.
 */
class RatingTriggerEngine(
    private val repository: RatingRepository,
    private val clock: CustClock,
) {
    @Suppress("ReturnCount")
    suspend fun evaluateEligibility(
        rules: RatingTriggerRules,
        cooldown: RatingCooldownPolicy,
    ): RatingEligibilityResult {
        val launchCount = repository.getLaunchCount()
        if (launchCount < rules.minimumAppLaunches) {
            return RatingEligibilityResult.MinimumLaunchesNotMet(launchCount, rules.minimumAppLaunches)
        }

        val eventCount = repository.getEventCount()
        if (eventCount < rules.requiredSignificantEvents) {
            return RatingEligibilityResult.MinimumEventsNotMet(eventCount, rules.requiredSignificantEvents)
        }

        val now = clock.currentTimeMillis()
        val installDate = repository.getInstallDate()
        val daysInstalled = TimeUnit.MILLISECONDS.toDays(now - installDate)
        if (daysInstalled < rules.minimumDaysInstalled) {
            return RatingEligibilityResult.MinimumDaysNotMet(daysInstalled, rules.minimumDaysInstalled)
        }

        val lastPromptDate = repository.getLastPromptDate()
        val lastReviewDate = repository.getLastReviewDate()
        val lastFeedbackDate = repository.getLastFeedbackDate()

        // Check Cooldowns
        if (lastPromptDate > 0) {
            val daysSincePrompt = TimeUnit.MILLISECONDS.toDays(now - lastPromptDate)
            if (daysSincePrompt < cooldown.daysAfterDismissal) {
                return RatingEligibilityResult.CooldownActive
            }
        }

        if (lastReviewDate > 0) {
            val daysSinceReview = TimeUnit.MILLISECONDS.toDays(now - lastReviewDate)
            if (daysSinceReview < cooldown.daysAfterPlayReviewLaunch) {
                return RatingEligibilityResult.CooldownActive
            }
        }

        if (lastFeedbackDate > 0) {
            val daysSinceFeedback = TimeUnit.MILLISECONDS.toDays(now - lastFeedbackDate)
            if (daysSinceFeedback < cooldown.daysAfterFeedbackRedirect) {
                return RatingEligibilityResult.CooldownActive
            }
        }

        return RatingEligibilityResult.Eligible
    }
}
