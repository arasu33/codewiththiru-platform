package com.codewiththiru.security.config

import com.codewiththiru.security.api.SecurityEnvironment

data class SecurityConfig(
    val environment: SecurityEnvironment = SecurityEnvironment.Production,
    val requireIntegrityCheck: Boolean = true,
    val requireCertificatePinning: Boolean = true
)
