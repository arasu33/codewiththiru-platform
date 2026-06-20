package com.codewiththiru.platform.coupons.domain.validator

import com.codewiththiru.platform.coupons.domain.model.CouponCampaign
import com.codewiththiru.platform.coupons.domain.model.CouponErrorCode
import com.codewiththiru.platform.coupons.domain.model.CouponModel
import com.codewiththiru.platform.coupons.domain.validator.rules.CampaignValidationRule
import com.codewiththiru.platform.coupons.domain.validator.rules.ExpiryValidationRule
import com.codewiththiru.platform.coupons.domain.validator.rules.FormatValidationRule
import com.codewiththiru.platform.coupons.provider.CouponClock
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CouponValidatorTest {

    private val fakeClock = object : CouponClock {
        override fun currentTimeMillis(): Long = 1000L
    }

    private val formatRule = FormatValidationRule()
    private val expiryRule = ExpiryValidationRule(fakeClock)
    private val campaignRule = CampaignValidationRule(fakeClock)

    private val compositeValidator = CompositeCouponModelValidator(
        listOf(expiryRule, campaignRule)
    )

    private val activeCampaign = CouponCampaign(
        id = "camp_1",
        name = "Summer Sale",
        startsAt = 500L,
        endsAt = 2000L,
        active = true
    )

    @Test
    fun testFormatValidation() = runBlocking {
        assertTrue(formatRule.isValid("VALID123"))
        assertFalse(formatRule.isValid("!NVALID")) // Invalid chars
        assertFalse(formatRule.isValid("SHRT")) // Too short
    }

    @Test
    fun testExpiryValidation_valid() = runBlocking {
        val model = CouponModel("CODE", activeCampaign, expiresAt = 2000L)
        assertNull(expiryRule.validate(model))
    }

    @Test
    fun testExpiryValidation_expired() = runBlocking {
        val model = CouponModel("CODE", activeCampaign, expiresAt = 500L)
        assertEquals(CouponErrorCode.Expired, expiryRule.validate(model))
    }

    @Test
    fun testCampaignValidation_inactive() = runBlocking {
        val inactiveCampaign = activeCampaign.copy(active = false)
        val model = CouponModel("CODE", inactiveCampaign, expiresAt = 2000L)
        assertEquals(CouponErrorCode.Expired, campaignRule.validate(model))
    }

    @Test
    fun testCampaignValidation_notStarted() = runBlocking {
        val futureCampaign = activeCampaign.copy(startsAt = 1500L)
        val model = CouponModel("CODE", futureCampaign, expiresAt = 2000L)
        assertEquals(CouponErrorCode.Expired, campaignRule.validate(model))
    }

    @Test
    fun testCompositeValidator_success() = runBlocking {
        val model = CouponModel("CODE", activeCampaign, expiresAt = 2000L)
        assertNull(compositeValidator.validate(model))
    }
}
