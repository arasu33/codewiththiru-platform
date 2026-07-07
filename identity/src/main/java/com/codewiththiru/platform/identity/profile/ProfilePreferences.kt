package com.codewiththiru.platform.identity.profile

data class ProfilePreferences(
    val languageCode: String = "en",
    val timeZone: String = "UTC",
    val themePreference: String = "SYSTEM",
)
