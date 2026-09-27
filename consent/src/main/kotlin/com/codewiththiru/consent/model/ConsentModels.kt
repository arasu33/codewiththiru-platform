package com.codewiththiru.consent.model

/**
 * Categories of user tracking, data collection, and processing consent.
 */
enum class ConsentCategory {
    /** Strictly necessary data collection for application core functionality. Always granted. */
    NECESSARY,

    /** Anonymous usage analytics, telemetry, and crash reports. */
    ANALYTICS,

    /** Personalized advertising and ad attribution. */
    ADVERTISING,

    /** Functional customizations, preferences, and personalizations. */
    FUNCTIONAL,
}

/**
 * Current state of user consent for a particular category.
 */
enum class ConsentStatus {
    /** User has explicitly granted consent. */
    GRANTED,

    /** User has explicitly denied consent. */
    DENIED,

    /** User has not yet made a selection. */
    NOT_CONFIGURED,
}

/**
 * Snapshot of all consent categories and the timestamp of the last update.
 */
data class ConsentSnapshot(
    val consents: Map<ConsentCategory, ConsentStatus>,
    val timestampMs: Long = System.currentTimeMillis(),
) {
    fun isGranted(category: ConsentCategory): Boolean {
        if (category == ConsentCategory.NECESSARY) return true
        return consents[category] == ConsentStatus.GRANTED
    }

    val hasUserResponded: Boolean
        get() = consents.values.any { it != ConsentStatus.NOT_CONFIGURED }
}
