package com.codewiththiru.platform.identity.profile

import com.codewiththiru.platform.identity.api.IdentityResult
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.test.runTest

class ProfileManagerTest {
    private val fakeRepo =
        object : ProfileRepository {
            override suspend fun fetchProfile(userId: String): IdentityResult<UserProfile> =
                IdentityResult.Success(
                    UserProfile("u1", "test@test.com", "Test", null, true, null, false, ProfilePreferences()),
                )

            override suspend fun saveProfile(profile: UserProfile) = IdentityResult.Success(Unit)
        }

    private val manager =
        object : ProfileManager {
            override suspend fun getProfile() = fakeRepo.fetchProfile("u1")

            override suspend fun updateProfile(profile: UserProfile) =
                fakeRepo.saveProfile(profile).let {
                    IdentityResult.Success(profile)
                }

            override suspend fun updatePreferences(preferences: ProfilePreferences) = IdentityResult.Success(Unit)
        }

    @Test
    fun testGetProfile() =
        runTest {
            val result = manager.getProfile()
            assertTrue(result is IdentityResult.Success)
        }
}
