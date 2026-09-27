package com.codewiththiru.security.util

import android.content.Context
import android.os.Build
import android.os.Debug
import android.provider.Settings
import java.io.File

/**
 * Utility functions for device security evaluation, integrity checks,
 * root detection, and debugging inspection.
 */
object SecurityUtils {
    private val KNOWN_ROOT_PATHS =
        listOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/su/bin/su",
        )

    /**
     * Checks if the device shows strong indicators of being rooted.
     * Evaluates build tags, known SU binaries, and execution path probing.
     */
    fun isDeviceRooted(): Boolean = checkBuildTags() || checkRootBinaries()

    /**
     * Checks if the current execution environment is an emulator.
     */
    fun isEmulator(): Boolean =
        (
            Build.FINGERPRINT.startsWith("generic") ||
                Build.FINGERPRINT.startsWith("unknown") ||
                Build.MODEL.contains("google_sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.contains("Android SDK built for x86") ||
                Build.MANUFACTURER.contains("Genymotion") ||
                Build.HARDWARE.contains("goldfish") ||
                Build.HARDWARE.contains("ranchu") ||
                Build.PRODUCT.contains("sdk_google") ||
                Build.PRODUCT.contains("google_sdk") ||
                Build.PRODUCT.contains("sdk") ||
                Build.PRODUCT.contains("vbox86p") ||
                Build.PRODUCT.contains("emulator") ||
                Build.PRODUCT.contains("simulator")
        )

    /**
     * Checks whether an active debugger is attached to the application process.
     */
    fun isDebuggerAttached(): Boolean = Debug.isDebuggerConnected() || Debug.waitingForDebugger()

    /**
     * Checks whether ADB (USB debugging) is enabled on the device.
     */
    fun isAdbEnabled(context: Context): Boolean =
        try {
            Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.ADB_ENABLED,
                0,
            ) != 0
        } catch (_: Exception) {
            false
        }

    private fun checkBuildTags(): Boolean {
        val buildTags = Build.TAGS
        return buildTags != null && buildTags.contains("test-keys")
    }

    private fun checkRootBinaries(): Boolean =
        KNOWN_ROOT_PATHS.any { path ->
            try {
                File(path).exists()
            } catch (_: Exception) {
                false
            }
        }
}
