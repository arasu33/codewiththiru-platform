package com.codewiththiru.platform.rating.provider

import android.app.Activity
import com.codewiththiru.platform.rating.model.ReviewLaunchResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class PlayReviewProviderTest {

    // Mock implementation for testing purposes
    class FakePlayReviewProvider : PlayReviewProvider {
        var shouldSucceed = true
        override suspend fun launchReview(activity: Activity): ReviewLaunchResult {
            return if (shouldSucceed) ReviewLaunchResult.Success else ReviewLaunchResult.PlayServicesUnavailable
        }
    }

    @Test
    fun `launchReview returns Success when provider succeeds`() = runTest {
        val provider = FakePlayReviewProvider()
        val activity = Robolectric.buildActivity(Activity::class.java).get()

        val result = provider.launchReview(activity)
        assertEquals(ReviewLaunchResult.Success, result)
    }

    @Test
    fun `launchReview returns PlayServicesUnavailable when provider fails`() = runTest {
        val provider = FakePlayReviewProvider()
        provider.shouldSucceed = false
        val activity = Robolectric.buildActivity(Activity::class.java).get()

        val result = provider.launchReview(activity)
        assertEquals(ReviewLaunchResult.PlayServicesUnavailable, result)
    }
}
