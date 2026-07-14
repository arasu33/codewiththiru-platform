package com.codewiththiru.notifications.api

import com.codewiththiru.notifications.config.NotificationChannelManager
import com.codewiththiru.notifications.provider.NotificationProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultNotificationManager(
    private val channelManager: NotificationChannelManager,
    private val provider: NotificationProvider
    // We will inject scheduler, personalization, and config here later
) : NotificationManager {

    private val _state = MutableStateFlow<NotificationState>(NotificationState.Uninitialized)
    override val state: StateFlow<NotificationState> = _state.asStateFlow()

    override fun initialize() {
        try {
            channelManager.createChannels()
            _state.value = NotificationState.Ready
        } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException || e is kotlin.coroutines.cancellation.CancellationException) throw e
            _state.value = NotificationState.Error(e)
        }
    }

    override suspend fun showNotification(payload: NotificationPayload): NotificationResult {
        if (_state.value != NotificationState.Ready) return NotificationResult.Failure(IllegalStateException("Not initialized"))
        
        // In the future: Add checks for quiet hours, consent, experiments here
        return provider.showNotification(payload)
    }

    override suspend fun scheduleNotification(payload: NotificationPayload, triggerAtMillis: Long): NotificationResult {
        // Will delegate to WorkManagerScheduler in Phase 17.2
        return NotificationResult.Success
    }

    override fun cancelNotification(id: String) {
        provider.cancelNotification(id)
    }

    override fun cancelAll() {
        provider.cancelAll()
    }

    override fun getActiveChannels(): List<String> {
        return channelManager.getActiveChannels()
    }
}
