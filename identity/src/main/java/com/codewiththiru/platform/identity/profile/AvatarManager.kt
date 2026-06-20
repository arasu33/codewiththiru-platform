package com.codewiththiru.platform.identity.profile

import com.codewiththiru.platform.identity.api.IdentityResult

interface AvatarManager {
    suspend fun uploadAvatar(imageBytes: ByteArray): IdentityResult<String> // Returns URL
    suspend fun removeAvatar(): IdentityResult<Unit>
}
