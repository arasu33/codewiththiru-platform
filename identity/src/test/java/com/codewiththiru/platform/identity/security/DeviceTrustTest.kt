package com.codewiththiru.platform.identity.security

import com.codewiththiru.platform.identity.api.IdentityResult
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.test.runTest

class DeviceTrustTest {
    private val fakeManager =
        object : DeviceTrustManager {
            private val trustedDevices = mutableListOf<TrustedDevice>()

            override suspend fun isDeviceTrusted(deviceId: String): Boolean =
                trustedDevices.any {
                    it.deviceId ==
                        deviceId
                }

            override suspend fun markDeviceAsTrusted(deviceId: String): IdentityResult<Unit> {
                trustedDevices.add(TrustedDevice(deviceId, "Test Device", "Android 14", 1000L, 1000L))
                return IdentityResult.Success(Unit)
            }

            override suspend fun removeTrustedDevice(deviceId: String): IdentityResult<Unit> {
                trustedDevices.removeIf { it.deviceId == deviceId }
                return IdentityResult.Success(Unit)
            }

            override suspend fun getTrustedDevices(): List<TrustedDevice> = trustedDevices

            override suspend fun analyzeDeviceRisk(deviceId: String): Float = 0.1f
        }

    @Test
    fun testTrustDevice() =
        runTest {
            val result = fakeManager.markDeviceAsTrusted("d1")
            assertTrue(result is IdentityResult.Success)
            assertTrue(fakeManager.isDeviceTrusted("d1"))
        }
}
