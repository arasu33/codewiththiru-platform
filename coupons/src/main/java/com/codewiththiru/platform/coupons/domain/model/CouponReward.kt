package com.codewiththiru.platform.coupons.domain.model

import java.math.BigDecimal

enum class DiscountType {
    PERCENTAGE,
    FIXED
}

sealed interface CouponReward {
    data class PremiumUnlock(val durationDays: Int) : CouponReward
    data class CoinsReward(val amount: Int) : CouponReward
    data class TrialExtension(val extraDays: Int) : CouponReward
    data class DiscountReward(val discountType: DiscountType, val discountValue: BigDecimal) : CouponReward
}
