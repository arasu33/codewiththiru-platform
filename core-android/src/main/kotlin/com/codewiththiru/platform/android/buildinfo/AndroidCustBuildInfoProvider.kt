package com.codewiththiru.platform.android.buildinfo

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.core.content.pm.PackageInfoCompat

class AndroidCustBuildInfoProvider(
    context: Context,
    override val buildType: String,
) : CustBuildInfoProvider {
    private val appContext: Context = context.applicationContext ?: context
    
    private val packageInfo by lazy {
        @Suppress("SwallowedException")
        try {
            appContext.packageManager.getPackageInfo(appContext.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

    override val packageName: String
        get() = appContext.packageName

    override val versionName: String
        get() = packageInfo?.versionName ?: ""

    override val versionCode: Long
        get() = packageInfo?.let { PackageInfoCompat.getLongVersionCode(it) } ?: 0L

    override val isDebug: Boolean
        get() = (appContext.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0

    override val firstInstallTime: Long
        get() = packageInfo?.firstInstallTime ?: 0L

    override val lastUpdateTime: Long
        get() = packageInfo?.lastUpdateTime ?: 0L
}
