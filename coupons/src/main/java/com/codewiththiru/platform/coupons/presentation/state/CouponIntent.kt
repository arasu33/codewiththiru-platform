package com.codewiththiru.platform.coupons.presentation.state

import com.codewiththiru.platform.coupons.domain.model.CouponSource

sealed interface CouponIntent {
    data class UpdateInput(val code: String) : CouponIntent()
    data class SubmitCoupon(val source: CouponSource = CouponSource.ManualEntry) : CouponIntent()
    object DismissError : CouponIntent()
    object DismissSuccess : CouponIntent()
}
