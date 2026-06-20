package com.codewiththiru.platform.coupons.api

import com.codewiththiru.platform.coupons.domain.model.CouponErrorCode
import com.codewiththiru.platform.coupons.domain.model.CouponModel
import com.codewiththiru.platform.coupons.domain.model.CouponReward
import com.codewiththiru.platform.coupons.domain.model.CouponSource

sealed interface CouponAnalyticsEvent {
    data class CouponEntered(val code: String, val source: CouponSource) : CouponAnalyticsEvent
    data class CouponValidated(val model: CouponModel) : CouponAnalyticsEvent
    data class CouponRedeemed(val reward: CouponReward) : CouponAnalyticsEvent
    data class CouponRejected(val code: String, val error: CouponErrorCode) : CouponAnalyticsEvent
    data class FraudDetected(val code: String) : CouponAnalyticsEvent
}
