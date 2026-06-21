package com.codewiththiru.remoteconfig.state

sealed interface RemoteConfigState {
    object Idle : RemoteConfigState
    object Loading : RemoteConfigState
    object Success : RemoteConfigState
    data class Error(val throwable: Throwable) : RemoteConfigState
}
