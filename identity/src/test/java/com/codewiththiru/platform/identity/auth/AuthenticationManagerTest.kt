package com.codewiththiru.platform.identity.auth

import com.codewiththiru.platform.identity.api.IdentityResult
import com.codewiththiru.platform.identity.token.AccessToken
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.test.runTest

class AuthenticationManagerTest {
    private val fakeProvider =
        object : AuthProvider {
            override val method = AuthMethod.EMAIL_PASSWORD

            override suspend fun authenticate(request: LoginRequest): IdentityResult<LoginResponse> =
                IdentityResult.Success(
                    LoginResponse(
                        session = AuthSession("s1", "u1", "d1", 1000L, false),
                        accessToken = AccessToken("token", 1000L),
                        refreshToken = null,
                    ),
                )
        }

    private val manager = DefaultAuthenticationManager(mapOf(AuthMethod.EMAIL_PASSWORD to fakeProvider))

    @Test
    fun testLoginSuccess() =
        runTest {
            val result = manager.login(LoginRequest(AuthMethod.EMAIL_PASSWORD))
            assertTrue(result is IdentityResult.Success)
            assertEquals("s1", (result as IdentityResult.Success).data.session.sessionId)
        }

    @Test
    fun testLoginFailure_NoProvider() =
        runTest {
            val result = manager.login(LoginRequest(AuthMethod.GOOGLE))
            assertTrue(result is IdentityResult.Failure)
        }
}
