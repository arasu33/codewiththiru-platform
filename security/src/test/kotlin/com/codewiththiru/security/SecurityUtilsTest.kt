package com.codewiththiru.security

import com.codewiththiru.security.util.SecurityUtils
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SecurityUtilsTest {
    @Test
    fun testRootDetectionRunsWithoutException() {
        val isRooted = SecurityUtils.isDeviceRooted()
        assertNotNull(isRooted)
    }

    @Test
    fun testEmulatorDetectionRunsWithoutException() {
        val isEmulator = SecurityUtils.isEmulator()
        assertNotNull(isEmulator)
    }

    @Test
    fun testDebuggerDetectionRunsWithoutException() {
        val isDebuggerAttached = SecurityUtils.isDebuggerAttached()
        assertNotNull(isDebuggerAttached)
    }
}
