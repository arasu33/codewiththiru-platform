package com.codewiththiru.platform.growth.repository

import com.codewiththiru.platform.growth.api.GrowthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface GrowthRepository {
    val state: Flow<GrowthState>
    suspend fun saveState(state: GrowthState)
}

class DefaultGrowthRepository : GrowthRepository {
    private val _state = MutableStateFlow<GrowthState>(GrowthState.Uninitialized)
    override val state: Flow<GrowthState> = _state.asStateFlow()

    override suspend fun saveState(state: GrowthState) {
        _state.value = state
    }
}
