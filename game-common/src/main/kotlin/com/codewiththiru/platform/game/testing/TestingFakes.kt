package com.codewiththiru.platform.game.testing

import com.codewiththiru.platform.analytics.api.AnalyticsManager
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import com.codewiththiru.platform.analytics.domain.event.AnalyticsScreen
import com.codewiththiru.platform.analytics.domain.event.AnalyticsUserProperty
import com.codewiththiru.platform.game.engine.GameTimer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeGameTimer : GameTimer {
    private val _timeFlow = MutableStateFlow(0L)
    override val timeFlow: StateFlow<Long> = _timeFlow.asStateFlow()

    var isRunning = false
        private set

    fun tick(seconds: Long = 1) {
        _timeFlow.value += seconds
    }

    override fun start(
        initialSeconds: Long,
        countdown: Boolean,
        maxSeconds: Long?,
    ) {
        _timeFlow.value = initialSeconds
        isRunning = true
    }

    override fun pause() {
        isRunning = false
    }

    override fun resume() {
        isRunning = true
    }

    override fun reset() {
        _timeFlow.value = 0L
        isRunning = false
    }

    override fun destroy() {
        reset()
    }
}

class FakeAnalyticsManager : AnalyticsManager {
    val trackedEvents = mutableListOf<AnalyticsEvent>()
    val trackedScreens = mutableListOf<AnalyticsScreen>()
    val userProperties = mutableMapOf<String, Any?>()

    override suspend fun track(event: AnalyticsEvent) {
        trackedEvents.add(event)
    }

    override suspend fun trackScreen(screen: AnalyticsScreen) {
        trackedScreens.add(screen)
    }

    override suspend fun setUserProperty(property: AnalyticsUserProperty) {
        userProperties[property.key] = property.value
    }

    override suspend fun flush() {
        // No-op
    }

    fun clear() {
        trackedEvents.clear()
        trackedScreens.clear()
        userProperties.clear()
    }
}
