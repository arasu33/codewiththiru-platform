package com.codewiththiru.platform.rating.policy

import com.codewiththiru.platform.rating.model.RatingEligibilityResult
import com.codewiththiru.platform.rating.repository.CustClock
import com.codewiththiru.platform.rating.repository.RatingRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.TimeUnit

class RatingTriggerEngineTest {

    private val repository: RatingRepository = mockk()
    
    private val clock: CustClock = object : CustClock {
        var mockedTime = 0L
        override fun currentTimeMillis(): Long = mockedTime
    }
    
    private val rules = RatingTriggerRules(
        minimumAppLaunches = 5,
        minimumDaysInstalled = 3,
        requiredSignificantEvents = 1
    )
    
    private val cooldown = RatingCooldownPolicy(
        daysAfterDismissal = 14,
        daysAfterFeedbackRedirect = 30,
        daysAfterPlayReviewLaunch = 90
    )

    private val engine = RatingTriggerEngine(repository, clock)

    @Test
    fun `returns MinimumLaunchesNotMet when launches are insufficient`() = runTest {
        coEvery { repository.getLaunchCount() } returns 2
        
        val result = engine.evaluateEligibility(rules, cooldown)
        assertTrue(result is RatingEligibilityResult.MinimumLaunchesNotMet)
    }

    @Test
    fun `returns Eligible when all conditions are met`() = runTest {
        coEvery { repository.getLaunchCount() } returns 6
        coEvery { repository.getEventCount() } returns 2
        coEvery { repository.getInstallDate() } returns 0L
        coEvery { repository.getLastPromptDate() } returns 0L
        coEvery { repository.getLastReviewDate() } returns 0L
        coEvery { repository.getLastFeedbackDate() } returns 0L
        
        (clock as CustClockImpl).mockedTime = TimeUnit.DAYS.toMillis(4)
        
        val result = engine.evaluateEligibility(rules, cooldown)
        assertEquals(RatingEligibilityResult.Eligible, result)
    }
    
    private abstract class CustClockImpl : CustClock {
        abstract var mockedTime: Long
    }
}
