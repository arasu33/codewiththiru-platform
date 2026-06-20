package com.codewiththiru.platform.growth.api

import com.codewiththiru.platform.growth.repository.GrowthRepository
import kotlinx.coroutines.flow.Flow

interface GrowthManager {
    val state: Flow<GrowthState>
    suspend fun initialize(config: GrowthConfig): GrowthResult<Unit>
    suspend fun shutdown()
}

class DefaultGrowthManager(
    private val repository: GrowthRepository
) : GrowthManager {
    
    override val state: Flow<GrowthState> = repository.state

    override suspend fun initialize(config: GrowthConfig): GrowthResult<Unit> {
        repository.saveState(GrowthState.Initializing)
        // Configuration loading and dependency initialization occurs here
        repository.saveState(GrowthState.Ready)
        return GrowthResult.Success(Unit)
    }

    override suspend fun shutdown() {
        repository.saveState(GrowthState.Uninitialized)
    }
}
