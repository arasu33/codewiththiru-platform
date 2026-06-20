package com.codewiththiru.platform.developer.repository

import com.codewiththiru.platform.developer.api.DeveloperPlatformState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface DeveloperPlatformRepository {
    val state: Flow<DeveloperPlatformState>
    suspend fun saveState(state: DeveloperPlatformState)
}

class DefaultDeveloperPlatformRepository : DeveloperPlatformRepository {
    private val _state = MutableStateFlow<DeveloperPlatformState>(DeveloperPlatformState.Uninitialized)
    override val state: Flow<DeveloperPlatformState> = _state.asStateFlow()

    override suspend fun saveState(state: DeveloperPlatformState) {
        _state.value = state
    }
}
