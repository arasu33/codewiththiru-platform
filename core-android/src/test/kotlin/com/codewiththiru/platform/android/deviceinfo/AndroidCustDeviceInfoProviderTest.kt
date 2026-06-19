package com.codewiththiru.platform.android.deviceinfo

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class AndroidCustDeviceInfoProviderTest {

    private lateinit var context: Context
    private lateinit var provider: AndroidCustDeviceInfoProvider

    @Before
    fun setup() {
        context = mockk(relaxed = true)
        provider = AndroidCustDeviceInfoProvider(context)
    }

    @Test
    fun `test build properties`() {
        ReflectionHelpers.setStaticField(Build::class.java, "MANUFACTURER", "TestManufacturer")
        ReflectionHelpers.setStaticField(Build::class.java, "BRAND", "TestBrand")
        ReflectionHelpers.setStaticField(Build::class.java, "MODEL", "TestModel")
        ReflectionHelpers.setStaticField(Build::class.java, "DEVICE", "TestDevice")
        ReflectionHelpers.setStaticField(Build.VERSION::class.java, "SDK_INT", 33)
        ReflectionHelpers.setStaticField(Build.VERSION::class.java, "RELEASE", "13")

        assertEquals("TestManufacturer", provider.manufacturer)
        assertEquals("TestBrand", provider.brand)
        assertEquals("TestModel", provider.model)
        assertEquals("TestDevice", provider.device)
        assertEquals(33, provider.sdkVersion)
        assertEquals("13", provider.androidVersion)
    }

    @Test
    fun `formFactor returns TABLET when screen is large`() {
        val configuration = Configuration().apply {
            screenLayout = Configuration.SCREENLAYOUT_SIZE_LARGE
        }
        val resources = mockk<Resources>()
        every { resources.configuration } returns configuration
        every { context.resources } returns resources
        
        ReflectionHelpers.setStaticField(Build.VERSION::class.java, "SDK_INT", 29)
        
        assertEquals(CustDeviceFormFactor.TABLET, provider.formFactor)
        assertTrue(provider.isTablet)
    }

    @Test
    fun `formFactor returns FOLDABLE when feature exists`() {
        val configuration = Configuration().apply {
            screenLayout = Configuration.SCREENLAYOUT_SIZE_NORMAL
        }
        val resources = mockk<Resources>()
        every { resources.configuration } returns configuration
        every { context.resources } returns resources
        
        val packageManager = mockk<PackageManager>()
        every { packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_HINGE_ANGLE) } returns true
        every { context.packageManager } returns packageManager
        
        ReflectionHelpers.setStaticField(Build.VERSION::class.java, "SDK_INT", Build.VERSION_CODES.R)
        
        assertEquals(CustDeviceFormFactor.FOLDABLE, provider.formFactor)
        assertFalse(provider.isTablet)
    }
    
    @Test
    fun `isEmulator returns true for generic fingerprint`() {
        ReflectionHelpers.setStaticField(Build::class.java, "FINGERPRINT", "generic_fingerprint")
        assertTrue(provider.isEmulator)
    }
}
