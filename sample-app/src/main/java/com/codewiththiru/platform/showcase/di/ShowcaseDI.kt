package com.codewiththiru.platform.showcase.di

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Manual DI container to hold platform managers
object ShowcaseDI {
    // Analytics
    val analyticsManager = DummyAnalyticsManager()
    
    // Remote Config
    val remoteConfigManager = DummyRemoteConfigManager()
    
    // Ads
    val adsManager = DummyAdsManager()
}

class DummyAnalyticsManager {
    private val _events = MutableStateFlow<List<String>>(emptyList())
    val events: StateFlow<List<String>> = _events

    fun trackEvent(name: String) {
        _events.value = _events.value + name
    }
    fun flush() {
        _events.value = emptyList()
    }
}

class DummyRemoteConfigManager {
    private val _config = MutableStateFlow<Map<String, String>>(mapOf("welcome_msg" to "Hello Platform"))
    val config: StateFlow<Map<String, String>> = _config

    fun fetch() {
        _config.value = _config.value + ("fetched_at" to System.currentTimeMillis().toString())
    }
}

class DummyAdsManager {
    private val _adsLoaded = MutableStateFlow(false)
    val adsLoaded: StateFlow<Boolean> = _adsLoaded

    fun loadAd() { _adsLoaded.value = true }
    fun showAd() { _adsLoaded.value = false }
}
