package com.codewiththiru.platform.android.deviceinfo

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build

class AndroidCustDeviceInfoProvider(
    context: Context,
) : CustDeviceInfoProvider {
    private val appContext: Context = context.applicationContext ?: context

    override val manufacturer: String
        get() = Build.MANUFACTURER

    override val brand: String
        get() = Build.BRAND

    override val model: String
        get() = Build.MODEL

    override val device: String
        get() = Build.DEVICE

    override val sdkVersion: Int
        get() = Build.VERSION.SDK_INT

    override val androidVersion: String
        get() = Build.VERSION.RELEASE

    override val isEmulator: Boolean
        get() =
            Build.FINGERPRINT.startsWith("generic") ||
                Build.FINGERPRINT.startsWith("unknown") ||
                Build.MODEL.contains("google_sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.contains("Android SDK built for x86") ||
                Build.MANUFACTURER.contains("Genymotion") ||
                (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) ||
                "google_sdk" == Build.PRODUCT

    override val isTablet: Boolean
        get() {
            return (
                appContext.resources.configuration.screenLayout and
                    Configuration.SCREENLAYOUT_SIZE_MASK
            ) >= Configuration.SCREENLAYOUT_SIZE_LARGE
        }

    override val formFactor: CustDeviceFormFactor
        get() {
            val packageManager = appContext.packageManager
            val isFoldable =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_HINGE_ANGLE)
                } else {
                    false
                }

            return when {
                isFoldable -> CustDeviceFormFactor.FOLDABLE
                isTablet -> CustDeviceFormFactor.TABLET
                else -> CustDeviceFormFactor.PHONE
            }
        }
}
