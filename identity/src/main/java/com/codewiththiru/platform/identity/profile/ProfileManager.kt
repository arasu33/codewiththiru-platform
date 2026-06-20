package com.codewiththiru.platform.identity.profile

import com.codewiththiru.platform.identity.api.IdentityResult

interface ProfileManager {
    suspend fun getProfile(): IdentityResult<UserProfile>
    suspend fun updateProfile(profile: UserProfile): IdentityResult<UserProfile>
    suspend fun updatePreferences(preferences: ProfilePreferences): IdentityResult<Unit>
}
