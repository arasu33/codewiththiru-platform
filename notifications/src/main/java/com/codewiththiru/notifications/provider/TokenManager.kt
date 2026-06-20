package com.codewiththiru.notifications.provider

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TokenManager(private val pushProvider: PushNotificationProvider) {

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token.asStateFlow()

    suspend fun refreshToken() {
        _token.value = pushProvider.getToken()
    }

    fun invalidate() {
        pushProvider.deleteToken()
        _token.value = null
    }
}
