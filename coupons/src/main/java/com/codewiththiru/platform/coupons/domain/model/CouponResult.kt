package com.codewiththiru.platform.coupons.domain.model

sealed interface CouponResult<out T> {
    data class Success<T>(val value: T) : CouponResult<T>
    data class Failure(val code: CouponErrorCode, val message: String) : CouponResult<Nothing>
}
