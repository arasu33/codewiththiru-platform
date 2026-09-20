package com.codewiththiru.platform.coupons.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewiththiru.platform.coupons.api.CouponAnalyticsEvent
import com.codewiththiru.platform.coupons.api.CouponAnalyticsProvider
import com.codewiththiru.platform.coupons.domain.model.CouponResult
import com.codewiththiru.platform.coupons.domain.model.CouponTriggerContext
import com.codewiththiru.platform.coupons.domain.repository.CouponRepository
import com.codewiththiru.platform.coupons.presentation.state.CouponEffect
import com.codewiththiru.platform.coupons.presentation.state.CouponIntent
import com.codewiththiru.platform.coupons.presentation.state.CouponUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CouponViewModel(
    private val repository: CouponRepository,
    private val analyticsProvider: CouponAnalyticsProvider,
    private val appPackageName: String,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CouponUIState())
    val uiState: StateFlow<CouponUIState> = _uiState.asStateFlow()

    private val _effect = kotlinx.coroutines.channels.Channel<CouponEffect>()
    val effect: kotlinx.coroutines.flow.Flow<CouponEffect> = _effect.receiveAsFlow()

    fun processIntent(intent: CouponIntent) {
        when (intent) {
            is CouponIntent.UpdateInput -> updateInput(intent.code)
            is CouponIntent.SubmitCoupon -> submitCoupon(intent)
            is CouponIntent.DismissError -> dismissError()
            is CouponIntent.DismissSuccess -> dismissSuccess()
        }
    }

    private fun updateInput(code: String) {
        _uiState.update { it.copy(inputCode = code) }
    }

    private fun submitCoupon(intent: CouponIntent.SubmitCoupon) {
        val currentCode = _uiState.value.inputCode.trim()
        if (currentCode.isEmpty()) return

        _uiState.update { it.copy(isLoading = true) }

        analyticsProvider.logEvent(CouponAnalyticsEvent.CouponEntered(currentCode, intent.source))

        viewModelScope.launch {
            val context =
                CouponTriggerContext(
                    appPackage = appPackageName,
                    feature = "coupon_entry",
                    source = intent.source,
                )

            when (val validationResult = repository.validateCoupon(currentCode, context)) {
                is CouponResult.Success -> {
                    analyticsProvider.logEvent(CouponAnalyticsEvent.CouponValidated(validationResult.value))
                    redeemCoupon(currentCode)
                }
                is CouponResult.Failure -> {
                    _uiState.update { it.copy(isLoading = false) }
                    analyticsProvider.logEvent(CouponAnalyticsEvent.CouponRejected(currentCode, validationResult.code))
                    _effect.send(CouponEffect.ShowErrorDialog(validationResult.message))
                }
            }
        }
    }

    private suspend fun redeemCoupon(code: String) {
        _uiState.update { it.copy(isRedeeming = true) }
        when (val redemptionResult = repository.redeemCoupon(code)) {
            is CouponResult.Success -> {
                _uiState.update { it.copy(isLoading = false, isRedeeming = false, inputCode = "") }
                analyticsProvider.logEvent(CouponAnalyticsEvent.CouponRedeemed(redemptionResult.value))
                _effect.send(CouponEffect.ShowSuccessDialog(redemptionResult.value))
            }
            is CouponResult.Failure -> {
                _uiState.update { it.copy(isLoading = false, isRedeeming = false) }
                _effect.send(CouponEffect.ShowErrorDialog(redemptionResult.message))
            }
        }
    }

    private fun dismissError() {
        // Handle error dismissal if state-based, but since we use effects for dialogs, this might be a no-op
        // or clear any lingering error state if we move from effects to state.
    }

    private fun dismissSuccess() {
        viewModelScope.launch {
            _effect.send(CouponEffect.NavigateBack)
        }
    }
}
