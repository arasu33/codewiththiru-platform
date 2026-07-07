package com.codewiththiru.platform.identity.profile

import com.codewiththiru.platform.identity.api.IdentityResult

interface ProfileRepository {
    suspend fun fetchProfile(userId: String): IdentityResult<UserProfile>

    suspend fun saveProfile(profile: UserProfile): IdentityResult<Unit>
}
