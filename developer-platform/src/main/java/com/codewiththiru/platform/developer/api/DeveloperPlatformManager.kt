package com.codewiththiru.platform.developer.api

import com.codewiththiru.platform.developer.repository.DeveloperPlatformRepository
import kotlinx.coroutines.flow.Flow

interface DeveloperPlatformManager {
    val state: Flow<DeveloperPlatformState>
    suspend fun initialize(config: DeveloperPlatformConfig): DeveloperPlatformResult<Unit>
    suspend fun shutdown()
}

class DefaultDeveloperPlatformManager(
    private val repository: DeveloperPlatformRepository
) : DeveloperPlatformManager {
    override val state: Flow<DeveloperPlatformState> = repository.state

    override suspend fun initialize(config: DeveloperPlatformConfig): DeveloperPlatformResult<Unit> {
        repository.saveState(DeveloperPlatformState.Initializing)
        repository.saveState(DeveloperPlatformState.Ready)
        return DeveloperPlatformResult.Success(Unit)
    }

    override suspend fun shutdown() {
        repository.saveState(DeveloperPlatformState.Uninitialized)
    }
}
