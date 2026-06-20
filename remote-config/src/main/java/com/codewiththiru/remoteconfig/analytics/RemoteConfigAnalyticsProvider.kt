package com.codewiththiru.remoteconfig.analytics

enum class RemoteConfigEvent {
    REMOTE_CONFIG_FETCH_STARTED,
    REMOTE_CONFIG_FETCH_SUCCESS,
    REMOTE_CONFIG_FETCH_FAILED,
    FEATURE_FLAG_EVALUATED,
    EXPERIMENT_ASSIGNED,
    KILL_SWITCH_TRIGGERED,
    CACHE_HIT,
    CACHE_MISS
}

interface RemoteConfigAnalyticsProvider {
    fun trackEvent(event: RemoteConfigEvent, params: Map<String, Any> = emptyMap())
}
