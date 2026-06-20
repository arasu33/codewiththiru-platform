package com.codewiththiru.platform.identity.security

import com.codewiththiru.platform.identity.api.IdentityResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class SecurityValidatorTest {

    private val validator = object : IdentitySecurityValidator {
        override suspend fun validateRequest(payload: String) = IdentityResult.Success(Unit)
        override suspend fun isSessionHijacked(sessionId: String) = false
    }

    @Test
    fun testValidateRequest() = runTest {
        val result = validator.validateRequest("payload")
        assertTrue(result is IdentityResult.Success)
    }
}
