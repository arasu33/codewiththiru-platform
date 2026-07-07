package com.codewiththiru.platform.identity.token

import com.codewiththiru.platform.identity.api.IdentityResult
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.test.runTest

class TokenManagerTest {
    private val manager =
        object : TokenManager {
            override suspend fun getAccessToken() = IdentityResult.Success(AccessToken("token", 1000L))

            override suspend fun refreshTokens() = IdentityResult.Success(AccessToken("new_token", 2000L))

            override suspend fun rotateTokens(refreshToken: RefreshToken) = IdentityResult.Success(Unit)

            override suspend fun clearTokens() {}
        }

    @Test
    fun testGetAccessToken() =
        runTest {
            val result = manager.getAccessToken()
            assertTrue(result is IdentityResult.Success)
        }
}
