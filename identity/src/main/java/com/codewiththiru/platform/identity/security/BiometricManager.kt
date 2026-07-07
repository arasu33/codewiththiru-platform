package com.codewiththiru.platform.identity.security

import com.codewiththiru.platform.identity.api.IdentityResult

interface BiometricManager {
    suspend fun isBiometricAvailable(): Boolean

    suspend fun authenticateWithBiometric(
        promptTitle: String,
        promptSubtitle: String,
    ): IdentityResult<Unit>
}
