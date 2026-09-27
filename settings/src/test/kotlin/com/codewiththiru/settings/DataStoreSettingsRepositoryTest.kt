package com.codewiththiru.settings

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.codewiththiru.settings.model.AppTheme
import com.codewiththiru.settings.repository.DataStoreSettingsRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

@RunWith(RobolectricTestRunner::class)
class DataStoreSettingsRepositoryTest {
    private lateinit var context: Context
    private lateinit var repository: DataStoreSettingsRepository

    @Before
    fun setUp() =
        runTest {
            context = ApplicationProvider.getApplicationContext()
            repository = DataStoreSettingsRepository(context)
            repository.setTheme(AppTheme.SYSTEM)
            repository.setDynamicColor(true)
        }

    @Test
    fun testDefaultSettingsAreValid() =
        runTest {
            val settings = repository.settings.first()
            assertEquals(AppTheme.SYSTEM, settings.theme)
        }

    @Test
    fun testSetThemeUpdatesState() =
        runTest {
            repository.setTheme(AppTheme.DARK)
            val settings = repository.settings.first()
            assertEquals(AppTheme.DARK, settings.theme)
        }

    @Test
    fun testSetDynamicColorUpdatesState() =
        runTest {
            repository.setDynamicColor(false)
            val settings = repository.settings.first()
            assertFalse(settings.dynamicColor)
        }
}
