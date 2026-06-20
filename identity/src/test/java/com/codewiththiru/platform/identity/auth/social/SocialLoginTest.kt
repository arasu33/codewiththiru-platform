package com.codewiththiru.platform.identity.auth.social

import com.codewiththiru.platform.identity.api.IdentityResult
import com.codewiththiru.platform.identity.auth.AuthMethod
import com.codewiththiru.platform.identity.auth.AuthenticationManager
import com.codewiththiru.platform.identity.auth.LoginRequest
import com.codewiththiru.platform.identity.auth.LoginResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class SocialLoginTest {

    private val fakeAuthManager = object : AuthenticationManager {
        override suspend fun login(request: LoginRequest): IdentityResult<LoginResponse> {
            return IdentityResult.Failure(com.codewiththiru.platform.identity.api.IdentityException.NetworkError())
        }
        override suspend fun logout(sessionId: String): IdentityResult<Unit> = IdentityResult.Success(Unit)
        override suspend fun getAvailableMethods(): List<AuthMethod> = emptyList()
    }

    private val socialManager = SocialLoginManager(fakeAuthManager)

    @Test
    fun testGoogleLogin_ForwardsToAuthManager() = runTest {
        val result = socialManager.loginWithGoogle("token")
        assertTrue(result is IdentityResult.Failure)
    }
}
