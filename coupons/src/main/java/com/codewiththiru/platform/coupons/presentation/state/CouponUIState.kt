package com.codewiththiru.platform.coupons.presentation.state

data class CouponUIState(
    val inputCode: String = "",
    val isLoading: Boolean = false,
    val isRedeeming: Boolean = false
)
