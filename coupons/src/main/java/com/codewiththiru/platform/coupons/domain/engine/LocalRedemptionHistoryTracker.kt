package com.codewiththiru.platform.coupons.domain.engine

import com.codewiththiru.platform.coupons.domain.model.CouponModel
import com.codewiththiru.platform.coupons.domain.model.CouponRedemptionHistory
import com.codewiththiru.platform.coupons.domain.model.CouponReward
import com.codewiththiru.platform.coupons.provider.CouponClock
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

class LocalRedemptionHistoryTracker(
    private val clock: CouponClock
) : RedemptionHistoryTracker {

    // Simple in-memory storage for architecture demonstration.
    // In production, this would be backed by Room or DataStore.
    private val history = ConcurrentHashMap<String, CopyOnWriteArrayList<CouponRedemptionHistory>>()

    override suspend fun recordRedemption(model: CouponModel, reward: CouponReward) {
        val entry = CouponRedemptionHistory(
            couponCode = model.code,
            redeemedAt = clock.currentTimeMillis(),
            campaignId = model.campaign.id,
            rewardGranted = reward
        )
        val list = history.getOrPut(model.code) { CopyOnWriteArrayList() }
        list.add(entry)
    }

    override suspend fun getHistory(couponCode: String): List<CouponRedemptionHistory> {
        return history[couponCode]?.toList() ?: emptyList()
    }

    override suspend fun hasBeenRedeemed(couponCode: String): Boolean {
        return getHistory(couponCode).isNotEmpty()
    }
}
