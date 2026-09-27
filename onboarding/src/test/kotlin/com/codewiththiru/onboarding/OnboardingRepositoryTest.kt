package com.codewiththiru.onboarding

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.codewiththiru.onboarding.repository.DataStoreOnboardingRepository
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

@RunWith(RobolectricTestRunner::class)
class OnboardingRepositoryTest {
    private lateinit var context: Context
    private lateinit var repository: DataStoreOnboardingRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        repository = DataStoreOnboardingRepository(context)
    }

    @Test
    fun testDefaultOnboardingIsNotCompleted() =
        runTest {
            val completed = repository.isOnboardingCompleted.first()
            assertFalse(completed)
        }

    @Test
    fun testSetOnboardingCompletedUpdatesState() =
        runTest {
            repository.setOnboardingCompleted(true)
            val completed = repository.isOnboardingCompleted.first()
            assertTrue(completed)
        }
}
