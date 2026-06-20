package com.codewiththiru.platform.identity.api

import com.codewiththiru.platform.identity.repository.IdentityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class IdentityManagerTest {

    private val fakeConfig = IdentityConfig()
    private val fakeRepo = object : IdentityRepository {
        override val currentState: Flow<IdentityState> = MutableStateFlow(IdentityState.Unauthenticated)
        override suspend fun getUserId(): String? = "user_123"
        override suspend fun isAnonymous(): Boolean = false
        override suspend fun logout(): IdentityResult<Unit> = IdentityResult.Success(Unit)
        override suspend fun refreshSession(): IdentityResult<Unit> = IdentityResult.Success(Unit)
    }

    private val manager = DefaultIdentityManager(fakeConfig, fakeRepo)

    @Test
    fun testGetCurrentUserId() = runTest {
        assertEquals("user_123", manager.getCurrentUserId())
    }

    @Test
    fun testLogout() = runTest {
        val result = manager.logout()
        assert(result is IdentityResult.Success)
    }
}
