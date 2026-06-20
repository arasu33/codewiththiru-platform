package com.codewiththiru.platform.identity.profile

data class UserProfile(
    val userId: String,
    val email: String?,
    val displayName: String?,
    val avatarUrl: String?,
    val isEmailVerified: Boolean,
    val phoneNumber: String?,
    val isPhoneVerified: Boolean,
    val preferences: ProfilePreferences
)
