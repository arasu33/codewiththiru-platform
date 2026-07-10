package com.codewiththiru.platform.analytics.data.provider

import android.os.Bundle
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent

/**
 * Maps AnalyticsEvent domain models into Android Bundles for Firebase Analytics.
 */
internal object FirebaseEventMapper {
    internal fun toBundle(event: AnalyticsEvent): Bundle {
        val bundle = Bundle()
        for ((key, value) in event.parameters) {
            when (value) {
                is String -> bundle.putString(key, value)
                is Int -> bundle.putInt(key, value)
                is Long -> bundle.putLong(key, value)
                is Double -> bundle.putDouble(key, value)
                is Float -> bundle.putFloat(key, value)
                is Boolean -> bundle.putBoolean(key, value)
                else -> bundle.putString(key, value.toString()) // Fallback
            }
        }
        return bundle
    }
}
