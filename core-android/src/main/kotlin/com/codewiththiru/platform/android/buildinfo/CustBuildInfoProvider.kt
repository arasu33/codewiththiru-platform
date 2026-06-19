package com.codewiththiru.platform.android.buildinfo

interface CustBuildInfoProvider {
    val packageName: String
    val versionName: String
    val versionCode: Long
    val buildType: String
    val isDebug: Boolean
    val firstInstallTime: Long
    val lastUpdateTime: Long
}
