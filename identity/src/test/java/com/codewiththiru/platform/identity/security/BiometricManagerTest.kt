package com.codewiththiru.platform.identity.security

import com.codewiththiru.platform.identity.api.IdentityResult
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.test.runTest

class BiometricManagerTest {
    @Test
    fun testBiometricAvailable() =
        runTest {
            val manager =
                object : BiometricManager {
                    override suspend fun isBiometricAvailable() = true

                    override suspend fun authenticateWithBiometric(
                        p1: String,
                        p2: String,
                    ) = IdentityResult.Success(Unit)
                }
            assertTrue(manager.isBiometricAvailable())
            assertTrue(manager.authenticateWithBiometric("T", "S") is IdentityResult.Success)
        }
}
