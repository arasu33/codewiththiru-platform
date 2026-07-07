package com.codewiththiru.platform.android.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class AndroidCustPermissionChecker(
    private val context: Context,
) : CustPermissionChecker {
    override fun hasPermission(permission: String): Boolean =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    override fun hasPermissions(vararg permissions: String): Boolean = permissions.all { hasPermission(it) }

    override fun missingPermissions(vararg permissions: String): List<String> =
        permissions.filterNot { hasPermission(it) }
}
