package com.codewiththiru.platform.coupons.domain.model

enum class CouponErrorCode {
    InvalidCode,
    Expired,
    AlreadyUsed,
    FraudDetected,
    CooldownActive,
    NetworkError,
    IntegrityFailure,
    Unknown
}
