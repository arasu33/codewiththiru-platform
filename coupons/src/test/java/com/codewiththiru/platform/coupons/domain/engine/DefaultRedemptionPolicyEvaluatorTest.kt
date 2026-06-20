package com.codewiththiru.platform.coupons.domain.engine

import com.codewiththiru.platform.coupons.config.CouponRedemptionPolicy
import com.codewiththiru.platform.coupons.domain.model.CouponCampaign
import com.codewiththiru.platform.coupons.domain.model.CouponErrorCode
import com.codewiththiru.platform.coupons.domain.model.CouponModel
import com.codewiththiru.platform.coupons.domain.model.CouponRedemptionHistory
import com.codewiththiru.platform.coupons.domain.model.CouponReward
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DefaultRedemptionPolicyEvaluatorTest {

    private val fakeHistoryTracker = object : RedemptionHistoryTracker {
        var mockHistory = emptyList<CouponRedemptionHistory>()
        override suspend fun recordRedemption(model: CouponModel, reward: CouponReward) {}
        override suspend fun getHistory(couponCode: String): List<CouponRedemptionHistory> = mockHistory
        override suspend fun hasBeenRedeemed(couponCode: String): Boolean = mockHistory.isNotEmpty()
    }

    private val evaluator = DefaultRedemptionPolicyEvaluator(fakeHistoryTracker)

    private val dummyModel = CouponModel(
        code = "CODE123",
        campaign = CouponCampaign("c1", "Camp", 0L, 1000L, true),
        expiresAt = 1000L
    )

    private val mockHistoryEntry = CouponRedemptionHistory(
        couponCode = "CODE123",
        redeemedAt = 500L,
        campaignId = "c1",
        rewardGranted = CouponReward.CoinsReward(10)
    )

    @Test
    fun testEvaluate_success() = runBlocking {
        val policy = CouponRedemptionPolicy(allowMultipleRedemptions = false, allowOfflineRedemption = true, maxRedemptionsPerUser = 1)
        fakeHistoryTracker.mockHistory = emptyList()

        val result = evaluator.evaluate(dummyModel, policy)
        assertNull(result)
    }

    @Test
    fun testEvaluate_alreadyUsed_singleUse() = runBlocking {
        val policy = CouponRedemptionPolicy(allowMultipleRedemptions = false, allowOfflineRedemption = true, maxRedemptionsPerUser = 1)
        fakeHistoryTracker.mockHistory = listOf(mockHistoryEntry)

        val result = evaluator.evaluate(dummyModel, policy)
        assertEquals(CouponErrorCode.AlreadyUsed, result)
    }

    @Test
    fun testEvaluate_maxRedemptionsExceeded() = runBlocking {
        val policy = CouponRedemptionPolicy(allowMultipleRedemptions = true, allowOfflineRedemption = true, maxRedemptionsPerUser = 2)
        fakeHistoryTracker.mockHistory = listOf(mockHistoryEntry, mockHistoryEntry)

        val result = evaluator.evaluate(dummyModel, policy)
        assertEquals(CouponErrorCode.AlreadyUsed, result)
    }
}
