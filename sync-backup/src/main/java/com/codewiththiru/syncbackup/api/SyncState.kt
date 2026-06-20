package com.codewiththiru.syncbackup.api

sealed class SyncState {
    object Idle : SyncState()
    object Syncing : SyncState()
    object BackingUp : SyncState()
    object Restoring : SyncState()
    data class Error(val error: SyncException) : SyncState()
}

sealed class SyncResult {
    object Success : SyncResult()
    data class Failure(val error: SyncException) : SyncResult()
}

sealed class SyncException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    class NetworkException(message: String) : SyncException(message)
    class ConflictException(message: String) : SyncException(message)
    class EncryptionException(message: String) : SyncException(message)
    class UnknownException(cause: Throwable) : SyncException(cause.message ?: "Unknown error", cause)
}
