package com.codewiththiru.platform.rating.provider

import com.codewiththiru.platform.rating.model.RatingAnalyticsEvent
import com.codewiththiru.platform.rating.model.RatingTriggerSource

/**
 * Generic callback to log events for the rating module.
 */
interface RatingAnalyticsProvider {
    fun logEvent(event: RatingAnalyticsEvent, source: RatingTriggerSource?, params: Map<String, Any> = emptyMap())
}
