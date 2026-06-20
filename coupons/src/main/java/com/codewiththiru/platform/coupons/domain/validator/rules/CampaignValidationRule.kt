package com.codewiththiru.platform.coupons.domain.validator.rules

import com.codewiththiru.platform.coupons.domain.model.CouponErrorCode
import com.codewiththiru.platform.coupons.domain.model.CouponModel
import com.codewiththiru.platform.coupons.domain.validator.CouponModelValidator
import com.codewiththiru.platform.coupons.provider.CouponClock

class CampaignValidationRule(
    private val clock: CouponClock
) : CouponModelValidator {
    override suspend fun validate(model: CouponModel): CouponErrorCode? {
        if (!model.campaign.active) {
            return CouponErrorCode.Expired // Reusing Expired for inactive campaigns, or InvalidCode
        }

        val now = clock.currentTimeMillis()
        if (now < model.campaign.startsAt || now > model.campaign.endsAt) {
            return CouponErrorCode.Expired
        }

        return null
    }
}
