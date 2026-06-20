package com.codewiththiru.remoteconfig.experiment

import com.codewiththiru.remoteconfig.analytics.RemoteConfigAnalyticsProvider
import com.codewiththiru.remoteconfig.analytics.RemoteConfigEvent

class ExperimentExposureTracker(
    private val analyticsProvider: RemoteConfigAnalyticsProvider
) {
    fun trackExposure(userId: String, experimentId: String, variantId: String) {
        analyticsProvider.trackEvent(
            RemoteConfigEvent.EXPERIMENT_ASSIGNED,
            mapOf(
                "userId" to userId,
                "experimentId" to experimentId,
                "variantId" to variantId
            )
        )
    }
}
