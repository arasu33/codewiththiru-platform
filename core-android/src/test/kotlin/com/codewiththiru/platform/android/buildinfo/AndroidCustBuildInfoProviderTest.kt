package com.codewiththiru.platform.android.buildinfo

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AndroidCustBuildInfoProviderTest {
    @Test
    fun `provider returns correct values from context`() {
        val context = mockk<Context>()
        val packageManager = mockk<PackageManager>()
        val packageInfo =
            PackageInfo().apply {
                versionName = "1.0.0"
                firstInstallTime = 1000L
                lastUpdateTime = 2000L
                // Suppress deprecation as we need to set it for testing older APIs if longVersionCode isn't accessible
                @Suppress("DEPRECATION")
                versionCode = 42
            }

        val applicationInfo =
            ApplicationInfo().apply {
                flags = ApplicationInfo.FLAG_DEBUGGABLE
            }

        every { context.packageName } returns "com.test.app"
        every { context.packageManager } returns packageManager
        every { context.applicationInfo } returns applicationInfo
        every { context.applicationContext } returns context
        every { packageManager.getPackageInfo("com.test.app", 0) } returns packageInfo

        val provider = AndroidCustBuildInfoProvider(context, "release")

        assertEquals("com.test.app", provider.packageName)
        assertEquals("1.0.0", provider.versionName)
        assertEquals(42L, provider.versionCode)
        assertEquals("release", provider.buildType)
        assertTrue(provider.isDebug)
        assertEquals(1000L, provider.firstInstallTime)
        assertEquals(2000L, provider.lastUpdateTime)
    }

    @Test
    fun `provider handles NameNotFoundException gracefully`() {
        val context = mockk<Context>()
        val packageManager = mockk<PackageManager>()
        val applicationInfo = ApplicationInfo()

        every { context.packageName } returns "com.test.app"
        every { context.packageManager } returns packageManager
        every { context.applicationInfo } returns applicationInfo
        every { context.applicationContext } returns context
        every { packageManager.getPackageInfo("com.test.app", 0) } throws PackageManager.NameNotFoundException()

        val provider = AndroidCustBuildInfoProvider(context, "debug")

        assertEquals("", provider.versionName)
        assertEquals(0L, provider.versionCode)
        assertFalse(provider.isDebug)
        assertEquals(0L, provider.firstInstallTime)
        assertEquals(0L, provider.lastUpdateTime)
    }
}
