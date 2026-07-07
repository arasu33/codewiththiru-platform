package com.codewiththiru.platform.identity.permission

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.test.runTest

class PermissionManagerTest {
    private val fakeManager =
        object : PermissionManager {
            private val userRoles = mutableMapOf("u1" to mutableSetOf(Role.USER))

            override suspend fun hasPermission(
                userId: String,
                permission: Permission,
            ): Boolean {
                val roles = userRoles[userId] ?: return false
                val permissions = AccessPolicy.getPermissionsForRoles(roles)
                return permissions.contains(permission)
            }

            override suspend fun hasRole(
                userId: String,
                role: Role,
            ): Boolean = userRoles[userId]?.contains(role) == true

            override suspend fun grantRole(
                userId: String,
                role: Role,
            ) {
                userRoles.getOrPut(userId) { mutableSetOf() }.add(role)
            }

            override suspend fun revokeRole(
                userId: String,
                role: Role,
            ) {
                userRoles[userId]?.remove(role)
            }

            override suspend fun getPermissions(userId: String): Set<Permission> {
                val roles = userRoles[userId] ?: return emptySet()
                return AccessPolicy.getPermissionsForRoles(roles)
            }
        }

    @Test
    fun testHasPermission() =
        runTest {
            assertTrue(fakeManager.hasPermission("u1", Permission.VIEW_CONTENT))
            assertFalse(fakeManager.hasPermission("u1", Permission.ACCESS_PREMIUM_FEATURES))

            fakeManager.grantRole("u1", Role.PREMIUM_USER)
            assertTrue(fakeManager.hasPermission("u1", Permission.ACCESS_PREMIUM_FEATURES))
        }
}
