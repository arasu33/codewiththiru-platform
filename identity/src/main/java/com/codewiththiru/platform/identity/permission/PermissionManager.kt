package com.codewiththiru.platform.identity.permission

interface PermissionManager {
    suspend fun hasPermission(userId: String, permission: Permission): Boolean
    suspend fun hasRole(userId: String, role: Role): Boolean
    suspend fun grantRole(userId: String, role: Role)
    suspend fun revokeRole(userId: String, role: Role)
    suspend fun getPermissions(userId: String): Set<Permission>
}
