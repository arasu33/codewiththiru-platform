package com.codewiththiru.platform.coupons.presentation.viewmodel

import com.codewiththiru.platform.coupons.api.CouponAnalyticsEvent
import com.codewiththiru.platform.coupons.api.CouponAnalyticsProvider
import com.codewiththiru.platform.coupons.data.repository.CouponRepository
import com.codewiththiru.platform.coupons.domain.model.CouponErrorCode
import com.codewiththiru.platform.coupons.domain.model.CouponModel
import com.codewiththiru.platform.coupons.domain.model.CouponResult
import com.codewiththiru.platform.coupons.domain.model.CouponReward
import com.codewiththiru.platform.coupons.domain.model.CouponTriggerContext
import com.codewiththiru.platform.coupons.presentation.state.CouponIntent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CouponViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val fakeRepository = object : CouponRepository {
        var validateResponse: CouponResult<CouponModel>? = null
        var redeemResponse: CouponResult<CouponReward>? = null

        override suspend fun validateCoupon(code: String, context: CouponTriggerContext): CouponResult<CouponModel> {
            return validateResponse ?: CouponResult.Failure(CouponErrorCode.Unknown, "Not mocked")
        }

        override suspend fun redeemCoupon(code: String): CouponResult<CouponReward> {
            return redeemResponse ?: CouponResult.Failure(CouponErrorCode.Unknown, "Not mocked")
        }

        override suspend fun getCachedCoupon(code: String): CouponModel? = null
        override suspend fun saveCoupon(coupon: CouponModel) {}
    }

    private val fakeAnalytics = object : CouponAnalyticsProvider {
        val events = mutableListOf<CouponAnalyticsEvent>()
        override fun logEvent(event: CouponAnalyticsEvent) {
            events.add(event)
        }
    }

    private lateinit classUnderTest: CouponViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        classUnderTest = CouponViewModel(
            repository = fakeRepository,
            analyticsProvider = fakeAnalytics,
            appPackageName = "com.test"
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testUpdateInput() = runTest {
        classUnderTest.processIntent(CouponIntent.UpdateInput("NEW_CODE"))
        val state = classUnderTest.uiState.value
        assertEquals("NEW_CODE", state.inputCode)
    }

    @Test
    fun testSubmitCoupon_EmptyInput_DoesNothing() = runTest {
        classUnderTest.processIntent(CouponIntent.SubmitCoupon())
        val state = classUnderTest.uiState.value
        assertFalse(state.isLoading)
        assertTrue(fakeAnalytics.events.isEmpty())
    }
}
