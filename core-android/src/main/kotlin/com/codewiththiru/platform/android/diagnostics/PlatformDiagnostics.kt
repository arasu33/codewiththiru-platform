package com.codewiththiru.platform.android.diagnostics

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.ContextCompat

/**
 * High-value zero-config utility for consumer apps to verify if the platform is properly configured.
 * This checks for required manifest permissions, internet connectivity, and general environment health.
 */
object PlatformDiagnostics {
    data class HealthReport(
        val isHealthy: Boolean,
        val issues: List<String>,
    )

    fun runHealthCheck(context: Context): HealthReport {
        val issues = mutableListOf<String>()

        // 1. Check Network State (Connectivity)
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val hasInternet =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = cm?.activeNetwork
                val caps = cm?.getNetworkCapabilities(network)
                caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
            } else {
                @Suppress("DEPRECATION")
                cm?.activeNetworkInfo?.isConnected == true
            }

        if (!hasInternet) {
            issues.add("Network: No active internet connection detected.")
        }

        // 2. Check Standard Permissions (e.g., Internet)
        val hasInternetPermission =
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.INTERNET,
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED

        if (!hasInternetPermission) {
            issues.add("Manifest: Missing <uses-permission android:name=\"android.permission.INTERNET\"/>")
        }

        return HealthReport(
            isHealthy = issues.isEmpty(),
            issues = issues,
        )
    }
}
