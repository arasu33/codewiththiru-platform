package com.codewiththiru.platform.identity.api

import com.codewiththiru.platform.identity.repository.IdentityRepository
import kotlinx.coroutines.flow.Flow

interface IdentityManager {
    val config: IdentityConfig
    val state: Flow<IdentityState>

    suspend fun initialize()

    suspend fun logout(): IdentityResult<Unit>

    suspend fun getCurrentUserId(): String?
}

class DefaultIdentityManager(
    override val config: IdentityConfig,
    private val repository: IdentityRepository,
) : IdentityManager {
    override val state: Flow<IdentityState> = repository.currentState

    override suspend fun initialize() {
        // Init logic (e.g., refresh token, validate session)
        repository.refreshSession()
    }

    override suspend fun logout(): IdentityResult<Unit> = repository.logout()

    override suspend fun getCurrentUserId(): String? = repository.getUserId()
}
