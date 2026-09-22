package com.codewiththiru.platform.android.diagnostics

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

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

        // 2. Run Comprehensive Setup Validation
        val setupIssues = PlatformSetupValidator.validate(context)
        setupIssues.forEach { issue ->
            issues.add("${issue.severity} [${issue.module}]: ${issue.message} -> Fix: ${issue.fix}")
        }

        // Filter out non-errors for the isHealthy flag (Warnings/Infos are OK)
        val hasErrors =
            setupIssues.any { it.severity == PlatformSetupValidator.Severity.ERROR } ||
                !hasInternet

        return HealthReport(
            isHealthy = !hasErrors,
            issues = issues,
        )
    }
}
