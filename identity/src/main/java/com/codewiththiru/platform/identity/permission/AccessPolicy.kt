package com.codewiththiru.platform.identity.permission

object AccessPolicy {
    private val rolePermissions = mapOf(
        Role.USER to setOf(Permission.VIEW_CONTENT),
        Role.PREMIUM_USER to setOf(Permission.VIEW_CONTENT, Permission.ACCESS_PREMIUM_FEATURES),
        Role.MODERATOR to setOf(Permission.VIEW_CONTENT, Permission.EDIT_CONTENT, Permission.CREATE_CONTENT),
        Role.ADMIN to Permission.values().toSet()
    )

    fun getPermissionsForRoles(roles: Set<Role>): Set<Permission> {
        return roles.flatMap { rolePermissions[it] ?: emptySet() }.toSet()
    }
}
