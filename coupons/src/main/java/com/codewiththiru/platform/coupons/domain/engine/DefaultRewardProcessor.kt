package com.codewiththiru.platform.coupons.domain.engine

import com.codewiththiru.platform.coupons.domain.model.CouponResult
import com.codewiththiru.platform.coupons.domain.model.CouponReward
import com.codewiththiru.platform.coupons.provider.CouponFeatureGate

class DefaultRewardProcessor(
    private val featureGate: CouponFeatureGate,
) : RewardProcessor {
    override suspend fun processReward(reward: CouponReward): CouponResult<Unit> =
        try {
            featureGate.unlock(reward)
            CouponResult.Success(Unit)
        } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException ||
                e is kotlin.coroutines.cancellation.CancellationException
            ) {
                throw e
            }
            // In a real implementation, we might want a specific error code for reward processing failures
            CouponResult.Failure(
                com.codewiththiru.platform.coupons.domain.model.CouponErrorCode.Unknown,
                e.message ?: "Failed to process reward",
            )
        }
}
