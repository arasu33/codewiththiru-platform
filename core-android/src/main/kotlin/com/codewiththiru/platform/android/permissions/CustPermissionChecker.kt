package com.codewiththiru.platform.android.permissions

interface CustPermissionChecker {
    fun hasPermission(permission: String): Boolean

    fun hasPermissions(vararg permissions: String): Boolean

    fun missingPermissions(vararg permissions: String): List<String>
}
