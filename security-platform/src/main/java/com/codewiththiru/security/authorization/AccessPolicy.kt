package com.codewiththiru.security.authorization

data class AccessPolicy(
    val requiredRoles: List<String>,
    val requiredPermissions: List<String>
)
