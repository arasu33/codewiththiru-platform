package com.codewiththiru.platform.observability.api

import kotlinx.coroutines.flow.Flow

/**
 * The core abstraction for capturing observability data from the CodeWithThiru platform.
 */
public interface ObservabilityManager {
    public suspend fun initialize()
    
    /** Synchronizes all offline telemetry data to the upstream sink. */
    public suspend fun flush(): ObservabilityResult<Unit>
    
    /** Provides a steady stream of internal operational status. */
    public fun observeState(): Flow<ObservabilityState>
}
