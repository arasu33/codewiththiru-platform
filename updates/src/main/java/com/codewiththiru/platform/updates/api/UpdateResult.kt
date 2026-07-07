package com.codewiththiru.platform.updates.api

/**
 * Represents the outcome of an update operation.
 */
sealed interface UpdateResult {
    data object Success : UpdateResult

    data object Cancelled : UpdateResult

    data object Deferred : UpdateResult

    data class Failure(
        val reason: String,
    ) : UpdateResult
}
