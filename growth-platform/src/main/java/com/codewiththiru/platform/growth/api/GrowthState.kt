package com.codewiththiru.platform.growth.api

sealed class GrowthState {
    object Uninitialized : GrowthState()
    object Initializing : GrowthState()
    object Ready : GrowthState()
    data class Error(val error: GrowthException) : GrowthState()
}
