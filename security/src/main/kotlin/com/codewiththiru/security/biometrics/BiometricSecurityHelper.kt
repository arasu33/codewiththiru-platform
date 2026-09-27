package com.codewiththiru.security.biometrics

import android.app.KeyguardManager
import android.content.Context
import android.os.Build

/**
 * Result of a biometric authentication attempt.
 */
sealed interface BiometricResult {
    data object Success : BiometricResult

    data class Failure(
        val reason: String,
    ) : BiometricResult

    data class Error(
        val code: Int,
        val message: String,
    ) : BiometricResult

    data object NotAvailable : BiometricResult

    data object NotEnrolled : BiometricResult
}

/**
 * High-level biometric and device-credential security helper.
 */
class BiometricSecurityHelper(
    private val context: Context,
) {
    private val keyguardManager: KeyguardManager? by lazy {
        context.getSystemService(Context.KEYGUARD_SERVICE) as? KeyguardManager
    }

    /**
     * Checks if the device has a secure lock screen (PIN, pattern, password, or biometric).
     */
    fun isDeviceSecure(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyguardManager?.isDeviceSecure == true
        } else {
            @Suppress("DEPRECATION")
            keyguardManager?.isKeyguardSecure == true
        }

    /**
     * Checks if the keyguard is currently locked.
     */
    fun isDeviceLocked(): Boolean = keyguardManager?.isDeviceLocked == true
}
