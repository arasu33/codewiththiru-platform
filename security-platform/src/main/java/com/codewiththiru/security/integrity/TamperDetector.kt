package com.codewiththiru.security.integrity

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

class TamperDetector(private val context: Context) {
    fun isAppTampered(): Boolean {
        return isDebuggable() || !isInstalledFromTrustedSource()
    }

    private fun isDebuggable(): Boolean {
        return (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
    }

    private fun isInstalledFromTrustedSource(): Boolean {
        return try {
            val installer = context.packageManager.getInstallerPackageName(context.packageName)
            installer == "com.android.vending" || installer == "com.amazon.venezia"
        } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: Exception) {
            false
        }
    }
}
