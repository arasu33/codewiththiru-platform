package com.codewiththiru.security.privacy

data class ConsentPolicy(
    val requiresGdprConsent: Boolean,
    val requiresCcpaConsent: Boolean
)
