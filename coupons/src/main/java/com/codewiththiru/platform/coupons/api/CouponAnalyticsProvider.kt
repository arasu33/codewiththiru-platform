package com.codewiththiru.platform.coupons.api

interface CouponAnalyticsProvider {
    fun logEvent(event: CouponAnalyticsEvent)
}
