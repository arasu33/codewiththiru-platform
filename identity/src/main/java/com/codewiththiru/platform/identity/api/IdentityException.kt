package com.codewiththiru.platform.identity.api

open class IdentityException(
    override val message: String,
    val code: String = "UNKNOWN_ERROR",
    override val cause: Throwable? = null
) : Exception(message, cause) {
    class NetworkError(message: String = "Network request failed") : IdentityException(message, "NETWORK_ERROR")
    class InvalidCredentials(message: String = "Invalid credentials") : IdentityException(message, "INVALID_CREDENTIALS")
    class SessionExpired(message: String = "Session expired") : IdentityException(message, "SESSION_EXPIRED")
    class UserNotFound(message: String = "User not found") : IdentityException(message, "USER_NOT_FOUND")
}
