@file:Suppress("MaxLineLength", "ArgumentListWrapping", "ImportOrdering")

package com.codewiththiru.platform.rating.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class RatingRepositoryTest {
    private val storageProvider: RatingStorageProvider = mockk(relaxed = true)
    private val clock: CustClock = mockk()

    private val repository = DefaultRatingRepository(storageProvider, clock)

    @Test
    fun `recordAppLaunch increments launch count and sets install date`() =
        runTest {
            coEvery { clock.currentTimeMillis() } returns 12345L

            repository.recordAppLaunch()

            coVerify { storageProvider.setInstallDate(12345L) }
            coVerify { storageProvider.incrementLaunchCount() }
        }

    @Test
    fun `get functions delegate to storage provider`() =
        runTest {
            coEvery { storageProvider.launchCount } returns flowOf(5)

            val count = repository.getLaunchCount()
            assertEquals(5, count)
        }
}
