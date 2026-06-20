package com.codewiththiru.platform.identity.security

import com.codewiththiru.platform.identity.api.IdentityResult

interface DeviceTrustManager {
    suspend fun isDeviceTrusted(deviceId: String): Boolean
    suspend fun markDeviceAsTrusted(deviceId: String): IdentityResult<Unit>
    suspend fun removeTrustedDevice(deviceId: String): IdentityResult<Unit>
    suspend fun getTrustedDevices(): List<TrustedDevice>
    suspend fun analyzeDeviceRisk(deviceId: String): Float // 0.0 to 1.0
}
