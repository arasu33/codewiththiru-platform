package com.codewiththiru.platform.android.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class AndroidCustPermissionCheckerTest {
    private lateinit var context: Context
    private lateinit var checker: AndroidCustPermissionChecker

    @Before
    fun setup() {
        context = RuntimeEnvironment.getApplication()
        checker = AndroidCustPermissionChecker(context)
        mockkStatic(ContextCompat::class)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `hasPermission returns true when granted`() {
        every { ContextCompat.checkSelfPermission(context, "PERMISSION_A") } returns PackageManager.PERMISSION_GRANTED
        assertTrue(checker.hasPermission("PERMISSION_A"))
    }

    @Test
    fun `hasPermission returns false when denied`() {
        every { ContextCompat.checkSelfPermission(context, "PERMISSION_A") } returns PackageManager.PERMISSION_DENIED
        assertFalse(checker.hasPermission("PERMISSION_A"))
    }

    @Test
    fun `hasPermissions returns true when all granted`() {
        every { ContextCompat.checkSelfPermission(context, "PERMISSION_A") } returns PackageManager.PERMISSION_GRANTED
        every { ContextCompat.checkSelfPermission(context, "PERMISSION_B") } returns PackageManager.PERMISSION_GRANTED

        assertTrue(checker.hasPermissions("PERMISSION_A", "PERMISSION_B"))
    }

    @Test
    fun `hasPermissions returns false when any denied`() {
        every { ContextCompat.checkSelfPermission(context, "PERMISSION_A") } returns PackageManager.PERMISSION_GRANTED
        every { ContextCompat.checkSelfPermission(context, "PERMISSION_B") } returns PackageManager.PERMISSION_DENIED

        assertFalse(checker.hasPermissions("PERMISSION_A", "PERMISSION_B"))
    }

    @Test
    fun `missingPermissions returns empty list when all granted`() {
        every { ContextCompat.checkSelfPermission(context, "PERMISSION_A") } returns PackageManager.PERMISSION_GRANTED
        every { ContextCompat.checkSelfPermission(context, "PERMISSION_B") } returns PackageManager.PERMISSION_GRANTED

        val missing = checker.missingPermissions("PERMISSION_A", "PERMISSION_B")
        assertTrue(missing.isEmpty())
    }

    @Test
    fun `missingPermissions returns list of denied permissions`() {
        every { ContextCompat.checkSelfPermission(context, "PERMISSION_A") } returns PackageManager.PERMISSION_GRANTED
        every { ContextCompat.checkSelfPermission(context, "PERMISSION_B") } returns PackageManager.PERMISSION_DENIED
        every { ContextCompat.checkSelfPermission(context, "PERMISSION_C") } returns PackageManager.PERMISSION_DENIED

        val missing = checker.missingPermissions("PERMISSION_A", "PERMISSION_B", "PERMISSION_C")
        assertEquals(listOf("PERMISSION_B", "PERMISSION_C"), missing)
    }
}
