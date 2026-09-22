package com.codewiththiru.platform.android.diagnostics

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

/**
 * Validates that the consumer app has properly configured the platform modules it depends on.
 *
 * Usage:
 * ```kotlin
 * val issues = PlatformSetupValidator.validate(context)
 * issues.forEach { issue ->
 *     Log.w("PlatformSetup", "${issue.severity}: [${issue.module}] ${issue.message}")
 * }
 * ```
 *
 * Call this during development/debug builds to catch configuration issues early.
 * Issues are non-fatal — they describe what might fail at runtime.
 */
object PlatformSetupValidator {
    enum class Severity { ERROR, WARNING, INFO }

    data class SetupIssue(
        val module: String,
        val severity: Severity,
        val message: String,
        val fix: String,
    )

    /**
     * Validates all detectable platform module configurations.
     * Returns an empty list if everything is properly configured.
     */
    fun validate(context: Context): List<SetupIssue> {
        val issues = mutableListOf<SetupIssue>()

        issues.addAll(validateFirebaseSetup(context))
        issues.addAll(validatePermissions(context))
        issues.addAll(validatePlayStore(context))
        issues.addAll(validateNotificationSetup(context))
        issues.addAll(validateBillingSetup(context))
        issues.addAll(validateAdsSetup(context))

        return issues
    }

    /**
     * Validates Firebase-dependent modules (analytics, notifications, remote-config).
     * Checks if google-services.json was processed by detecting the generated resource.
     */
    private fun validateFirebaseSetup(context: Context): List<SetupIssue> {
        val issues = mutableListOf<SetupIssue>()

        // Check if google-services.json was processed (generates R.string.google_app_id)
        val googleAppIdRes =
            context.resources.getIdentifier(
                "google_app_id",
                "string",
                context.packageName,
            )
        if (googleAppIdRes == 0) {
            issues.add(
                SetupIssue(
                    module = "analytics, notifications, remote-config",
                    severity = Severity.ERROR,
                    message = "google-services.json not found or Google Services plugin not applied.",
                    fix =
                        "1. Download google-services.json from Firebase Console\n" +
                            "   2. Place it in your app/ module root\n" +
                            "   3. Apply plugin: id(\"com.google.gms.google-services\") in app/build.gradle.kts",
                ),
            )
        }

        return issues
    }

    /**
     * Validates required manifest permissions for platform modules.
     */
    private fun validatePermissions(context: Context): List<SetupIssue> {
        val issues = mutableListOf<SetupIssue>()
        val pm = context.packageManager
        val packageInfo =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pm.getPackageInfo(
                    context.packageName,
                    PackageManager.PackageInfoFlags.of(PackageManager.GET_PERMISSIONS.toLong()),
                )
            } else {
                @Suppress("DEPRECATION")
                pm.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)
            }

        val declaredPermissions = packageInfo.requestedPermissions?.toSet() ?: emptySet()

        // INTERNET — needed by analytics, notifications, remote-config, ads
        if ("android.permission.INTERNET" !in declaredPermissions) {
            issues.add(
                SetupIssue(
                    module = "core-android",
                    severity = Severity.ERROR,
                    message = "Missing INTERNET permission in AndroidManifest.xml",
                    fix = "<uses-permission android:name=\"android.permission.INTERNET\" />",
                ),
            )
        }

        // ACCESS_NETWORK_STATE — needed by analytics, diagnostics
        if ("android.permission.ACCESS_NETWORK_STATE" !in declaredPermissions) {
            issues.add(
                SetupIssue(
                    module = "analytics",
                    severity = Severity.WARNING,
                    message = "Missing ACCESS_NETWORK_STATE permission (recommended for offline analytics)",
                    fix = "<uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />",
                ),
            )
        }

        // POST_NOTIFICATIONS — needed by notifications on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if ("android.permission.POST_NOTIFICATIONS" !in declaredPermissions) {
                issues.add(
                    SetupIssue(
                        module = "notifications",
                        severity = Severity.WARNING,
                        message = "Missing POST_NOTIFICATIONS permission (required for Android 13+)",
                        fix =
                            "<uses-permission android:name=\"android.permission.POST_NOTIFICATIONS\" />\n" +
                                "   Also request this permission at runtime via ActivityCompat.requestPermissions()",
                    ),
                )
            }
        }

        return issues
    }

    /**
     * Validates Google Play Store availability (needed by billing, rating, updates).
     */
    private fun validatePlayStore(context: Context): List<SetupIssue> {
        val issues = mutableListOf<SetupIssue>()

        val playStoreAvailable =
            try {
                context.packageManager.getPackageInfo("com.android.vending", 0)
                true
            } catch (_: PackageManager.NameNotFoundException) {
                false
            }

        if (!playStoreAvailable) {
            issues.add(
                SetupIssue(
                    module = "billing, rating, updates",
                    severity = Severity.WARNING,
                    message =
                        "Google Play Store not found on this device. " +
                            "Billing, in-app rating, and in-app updates will not function.",
                    fix = "Test on a device/emulator with Google Play Services installed.",
                ),
            )
        }

        return issues
    }

    /**
     * Validates notification-specific setup (channels, default icon metadata).
     */
    private fun validateNotificationSetup(context: Context): List<SetupIssue> {
        val issues = mutableListOf<SetupIssue>()

        // Check for default notification channel metadata
        try {
            val appInfo =
                context.packageManager.getApplicationInfo(
                    context.packageName,
                    PackageManager.GET_META_DATA,
                )
            val metaData = appInfo.metaData
            val channelId =
                metaData?.getString(
                    "com.google.firebase.messaging.default_notification_channel_id",
                )
            if (channelId.isNullOrEmpty()) {
                issues.add(
                    SetupIssue(
                        module = "notifications",
                        severity = Severity.INFO,
                        message = "No default FCM notification channel configured in manifest metadata.",
                        fix =
                            "<meta-data\n" +
                                "    android:name=\"com.google.firebase.messaging.default_notification_channel_id\"\n" +
                                "    android:value=\"@string/default_notification_channel_id\" />",
                    ),
                )
            }
        } catch (_: Exception) {
            // Metadata not available — skip this check
        }

        return issues
    }

    /**
     * Validates billing-specific setup (BILLING permission).
     */
    private fun validateBillingSetup(context: Context): List<SetupIssue> {
        val issues = mutableListOf<SetupIssue>()

        val pm = context.packageManager
        val packageInfo =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pm.getPackageInfo(
                    context.packageName,
                    PackageManager.PackageInfoFlags.of(PackageManager.GET_PERMISSIONS.toLong()),
                )
            } else {
                @Suppress("DEPRECATION")
                pm.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)
            }
        val declaredPermissions = packageInfo.requestedPermissions?.toSet() ?: emptySet()

        if ("com.android.vending.BILLING" !in declaredPermissions) {
            issues.add(
                SetupIssue(
                    module = "billing",
                    severity = Severity.WARNING,
                    message = "Missing BILLING permission. Required if using the billing module.",
                    fix = "<uses-permission android:name=\"com.android.vending.BILLING\" />",
                ),
            )
        }

        return issues
    }

    /**
     * Validates ads-specific setup (AdMob APPLICATION_ID metadata).
     */
    private fun validateAdsSetup(context: Context): List<SetupIssue> {
        val issues = mutableListOf<SetupIssue>()

        try {
            val appInfo =
                context.packageManager.getApplicationInfo(
                    context.packageName,
                    PackageManager.GET_META_DATA,
                )
            val metaData = appInfo.metaData
            val adMobId = metaData?.getString("com.google.android.gms.ads.APPLICATION_ID")
            if (adMobId.isNullOrEmpty()) {
                issues.add(
                    SetupIssue(
                        module = "ads",
                        severity = Severity.WARNING,
                        message =
                            "No AdMob APPLICATION_ID configured in manifest metadata. " +
                                "Required if using the ads module.",
                        fix =
                            "<meta-data\n" +
                                "    android:name=\"com.google.android.gms.ads.APPLICATION_ID\"\n" +
                                "    android:value=\"ca-app-pub-XXXXXXXXXXXXXXXX~XXXXXXXXXX\" />",
                    ),
                )
            }
        } catch (_: Exception) {
            // Metadata not available — skip this check
        }

        return issues
    }
}
