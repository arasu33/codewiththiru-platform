package com.codewiththiru.platform.moreapps.presentation.integration

interface AppInstallResolver {
    fun isInstalled(packageName: String): Boolean
    fun isUpdateAvailable(packageName: String): Boolean
}
