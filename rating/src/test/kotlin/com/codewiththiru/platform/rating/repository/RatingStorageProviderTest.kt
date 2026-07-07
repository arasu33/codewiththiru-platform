package com.codewiththiru.platform.rating.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class RatingStorageProviderTest {
    private lateinit var storageProvider: RatingStorageProviderImpl
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        storageProvider = RatingStorageProviderImpl(context)
    }

    @Test
    fun `incrementLaunchCount increases count by one`() =
        runTest {
            storageProvider.incrementLaunchCount()
            var count = storageProvider.launchCount.first()
            assertEquals(1, count)

            storageProvider.incrementLaunchCount()
            count = storageProvider.launchCount.first()
            assertEquals(2, count)
        }

    @Test
    fun `setInstallDate only sets once`() =
        runTest {
            storageProvider.setInstallDate(100L)
            assertEquals(100L, storageProvider.installDate.first())

            storageProvider.setInstallDate(200L)
            // Should remain 100L
            assertEquals(100L, storageProvider.installDate.first())
        }
}
