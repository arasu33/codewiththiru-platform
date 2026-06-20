package com.codewiththiru.platform.observability.repository

import com.codewiththiru.platform.observability.api.ObservabilityState
import kotlinx.coroutines.flow.Flow

/**
 * Contract for persisting diagnostic logs, traces, and metrics before they are synced.
 */
public interface ObservabilityRepository {
    public fun observeState(): Flow<ObservabilityState>
    public suspend fun commitState(state: ObservabilityState)
    public suspend fun syncPendingPayloads(): Boolean
}
