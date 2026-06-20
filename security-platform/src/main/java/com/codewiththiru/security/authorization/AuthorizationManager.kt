package com.codewiththiru.security.authorization

interface AuthorizationManager {
    fun hasPermission(userId: String, permission: String): Boolean
    fun hasRole(userId: String, role: String): Boolean
}
