package com.codewiththiru.platform.identity.repository

import com.codewiththiru.platform.identity.api.IdentityResult
import com.codewiththiru.platform.identity.api.IdentityState
import kotlinx.coroutines.flow.Flow

interface IdentityRepository {
    val currentState: Flow<IdentityState>

    suspend fun getUserId(): String?

    suspend fun isAnonymous(): Boolean

    suspend fun logout(): IdentityResult<Unit>

    suspend fun refreshSession(): IdentityResult<Unit>
}
