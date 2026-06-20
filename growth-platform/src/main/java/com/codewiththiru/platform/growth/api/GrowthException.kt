package com.codewiththiru.platform.growth.api

sealed class GrowthException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    class NetworkException(message: String) : GrowthException(message)
    class ValidationException(message: String) : GrowthException(message)
    class NotInitializedException : GrowthException("GrowthManager is not initialized")
    class PersistenceException(message: String, cause: Throwable? = null) : GrowthException(message, cause)
}
