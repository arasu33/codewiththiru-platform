@file:Suppress("MaxLineLength")

package com.codewiththiru.platform.rating.repository

import com.codewiththiru.platform.rating.model.RatingAnalyticsEvent
import com.codewiththiru.platform.rating.model.RatingTriggerSource
import kotlinx.coroutines.flow.first

/**
 * Central orchestrator for rating persistence, analytics, and metadata.
 */
@Suppress("TooManyFunctions")
interface RatingRepository {
    suspend fun getLaunchCount(): Int

    suspend fun getEventCount(): Int

    suspend fun getInstallDate(): Long

    suspend fun getLastPromptDate(): Long

    suspend fun getLastReviewDate(): Long

    suspend fun getLastFeedbackDate(): Long

    suspend fun recordAppLaunch()

    suspend fun recordSignificantEvent()

    suspend fun recordPromptShown()

    suspend fun recordReviewLaunched()

    suspend fun recordFeedbackRedirected()

    fun logAnalyticsEvent(
        event: RatingAnalyticsEvent,
        source: RatingTriggerSource?,
        params: Map<String, Any> = emptyMap(),
    )
}

class DefaultRatingRepository(
    private val storageProvider: RatingStorageProvider,
    private val clock: CustClock,
) : RatingRepository {
    override suspend fun getLaunchCount(): Int = storageProvider.launchCount.first()

    override suspend fun getEventCount(): Int = storageProvider.eventCount.first()

    override suspend fun getInstallDate(): Long = storageProvider.installDate.first()

    override suspend fun getLastPromptDate(): Long = storageProvider.lastPromptDate.first()

    override suspend fun getLastReviewDate(): Long = storageProvider.lastReviewDate.first()

    override suspend fun getLastFeedbackDate(): Long = storageProvider.lastFeedbackDate.first()

    override suspend fun recordAppLaunch() {
        val now = clock.currentTimeMillis()
        storageProvider.setInstallDate(now) // Only sets if null
        storageProvider.incrementLaunchCount()
    }

    override suspend fun recordSignificantEvent() {
        storageProvider.incrementEventCount()
    }

    override suspend fun recordPromptShown() {
        storageProvider.setLastPromptDate(clock.currentTimeMillis())
    }

    override suspend fun recordReviewLaunched() {
        storageProvider.setLastReviewDate(clock.currentTimeMillis())
    }

    override suspend fun recordFeedbackRedirected() {
        storageProvider.setLastFeedbackDate(clock.currentTimeMillis())
    }

    override fun logAnalyticsEvent(
        event: RatingAnalyticsEvent,
        source: RatingTriggerSource?,
        params: Map<String, Any>,
    ) {
        // Analytics disabled for now
    }
}
