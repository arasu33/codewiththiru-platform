package com.codewiththiru.platform.coupons.domain.engine

import com.codewiththiru.platform.coupons.config.CouponRedemptionPolicy
import com.codewiththiru.platform.coupons.domain.model.CouponErrorCode
import com.codewiththiru.platform.coupons.domain.model.CouponModel

class DefaultRedemptionPolicyEvaluator(
    private val historyTracker: RedemptionHistoryTracker,
) : RedemptionPolicyEvaluator {
    override suspend fun evaluate(
        model: CouponModel,
        policy: CouponRedemptionPolicy,
    ): CouponErrorCode? {
        val pastRedemptions = historyTracker.getHistory(model.code)

        if (!policy.allowMultipleRedemptions && pastRedemptions.isNotEmpty()) {
            return CouponErrorCode.AlreadyUsed
        }

        if (pastRedemptions.size >= policy.maxRedemptionsPerUser) {
            return CouponErrorCode.AlreadyUsed
        }

        return null
    }
}
