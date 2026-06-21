package com.codewiththiru.notifications.api

sealed class NotificationResult {
    object Success : NotificationResult()

    data class Failure(val error: Throwable) : NotificationResult()

    object Suppressed : NotificationResult() // Quiet hours, missing consent, etc.
}

sealed class NotificationState {
    object Uninitialized : NotificationState()

    object Ready : NotificationState()

    data class Error(val throwable: Throwable) : NotificationState()
}
