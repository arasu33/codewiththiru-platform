package com.codewiththiru.platform.coupons.presentation.state

import com.codewiththiru.platform.coupons.domain.model.CouponReward

sealed interface CouponEffect {
    data class ShowSnackbar(
        val message: String,
    ) : CouponEffect

    data object NavigateBack : CouponEffect

    data class ShowSuccessDialog(
        val reward: CouponReward,
    ) : CouponEffect

    data class ShowErrorDialog(
        val reason: String,
    ) : CouponEffect

    data class LaunchRewardScreen(
        val reward: CouponReward,
    ) : CouponEffect
}
