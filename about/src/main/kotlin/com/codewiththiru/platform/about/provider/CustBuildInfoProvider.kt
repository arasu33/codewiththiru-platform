package com.codewiththiru.platform.about.provider

import com.codewiththiru.platform.about.model.AppInfo

/**
 * Abstraction to provide generic application build data mapped dynamically.
 */
interface CustBuildInfoProvider {
    fun provideAppInfo(): AppInfo
}
