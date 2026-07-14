package com.codewiththiru.ads.provider.admob

import com.google.android.gms.ads.MobileAds

/**
 * Collects AdMob-specific diagnostics data.
 */
object AdMobDiagnostics {

    fun getVersionString(): String {
        return MobileAds.getVersion().toString()
    }

    fun getInitializationStatus(): Map<String, String> {
        val statusMap = java.util.concurrent.ConcurrentHashMap<String, String>()
        val initStatus = MobileAds.getInitializationStatus()
        initStatus?.adapterStatusMap?.forEach { (adapterClass, status) ->
            statusMap[adapterClass] = status.description
        }
        return statusMap
    }
}
