package com.codewiththiru.platform.developer.api

sealed class DeveloperPlatformState {
    object Uninitialized : DeveloperPlatformState()
    object Initializing : DeveloperPlatformState()
    object Ready : DeveloperPlatformState()
    data class Error(val message: String) : DeveloperPlatformState()
}
