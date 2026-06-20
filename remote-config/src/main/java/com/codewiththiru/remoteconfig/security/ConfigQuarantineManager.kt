package com.codewiththiru.remoteconfig.security

import android.util.Log
import com.codewiththiru.remoteconfig.analytics.RemoteConfigAnalyticsProvider
import com.codewiththiru.remoteconfig.analytics.RemoteConfigEvent

class ConfigQuarantineManager(
    private val analyticsProvider: RemoteConfigAnalyticsProvider
) {
    private val quarantinedConfigs = mutableListOf<String>()

    fun processConfig(payload: String, riskLevel: ConfigRiskLevel): Boolean {
        return when (riskLevel) {
            ConfigRiskLevel.LOW -> true
            ConfigRiskLevel.MEDIUM -> {
                Log.w("ConfigQuarantine", "Medium risk config processed with warning.")
                true
            }
            ConfigRiskLevel.HIGH -> {
                Log.e("ConfigQuarantine", "High risk config blocked.")
                analyticsProvider.trackEvent(RemoteConfigEvent.REMOTE_CONFIG_FETCH_FAILED, mapOf("reason" to "high_risk"))
                false
            }
            ConfigRiskLevel.CRITICAL -> {
                Log.e("ConfigQuarantine", "Critical risk config quarantined.")
                quarantinedConfigs.add(payload)
                analyticsProvider.trackEvent(RemoteConfigEvent.REMOTE_CONFIG_FETCH_FAILED, mapOf("reason" to "critical_risk"))
                false
            }
        }
    }
}
