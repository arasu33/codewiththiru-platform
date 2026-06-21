package com.codewiththiru.notifications.api

sealed class NotificationException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    class PermissionDeniedException(message: String) : NotificationException(message)

    class MissingConsentException(message: String) : NotificationException(message)

    class ProviderNotInitializedException(message: String) : NotificationException(message)

    class DeliveryFailedException(message: String, cause: Throwable? = null) : NotificationException(message, cause)
}
