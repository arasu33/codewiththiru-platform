package com.codewiththiru.platform.rating.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewiththiru.platform.rating.model.RatingAnalyticsEvent
import com.codewiththiru.platform.rating.model.RatingConfig
import com.codewiththiru.platform.rating.model.RatingResult
import com.codewiththiru.platform.rating.model.RatingTriggerSource
import com.codewiththiru.platform.rating.repository.RatingRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RatingViewModel(
    private val config: RatingConfig,
    private val repository: RatingRepository,
    private val triggerSource: RatingTriggerSource? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(RatingUiState(promptType = config.promptType))
    val uiState: StateFlow<RatingUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<RatingEffect>()
    val effect: SharedFlow<RatingEffect> = _effect.asSharedFlow()

    private val _result = MutableSharedFlow<RatingResult>()
    val result: SharedFlow<RatingResult> = _result.asSharedFlow()

    fun onAction(action: RatingAction) {
        when (action) {
            is RatingAction.PromptShown -> {
                viewModelScope.launch {
                    repository.recordPromptShown()
                    repository.logAnalyticsEvent(RatingAnalyticsEvent.PromptShown, triggerSource)
                }
            }
            is RatingAction.StarSelected -> {
                _uiState.update { it.copy(selectedStars = action.stars) }
                repository.logAnalyticsEvent(
                    RatingAnalyticsEvent.StarSelected,
                    triggerSource,
                    mapOf("stars" to action.stars)
                )
            }
            is RatingAction.SubmitClicked -> {
                handleSubmit()
            }
            is RatingAction.DismissClicked -> {
                viewModelScope.launch {
                    repository.logAnalyticsEvent(RatingAnalyticsEvent.Dismissed, triggerSource)
                    _result.emit(RatingResult.Dismissed)
                    _effect.emit(RatingEffect.ClosePrompt)
                }
            }
        }
    }

    private fun handleSubmit() {
        val stars = _uiState.value.selectedStars
        if (stars == 0) return // Cannot submit 0 stars

        _uiState.update { it.copy(isSubmitting = true) }
        repository.logAnalyticsEvent(
            RatingAnalyticsEvent.SubmitClicked,
            triggerSource,
            mapOf("stars" to stars)
        )

        viewModelScope.launch {
            if (stars >= config.playReviewThreshold) {
                // Flow A: Launch Play Review
                _effect.emit(
                    RatingEffect.LaunchPlayReview {
                        viewModelScope.launch {
                            repository.recordReviewLaunched()
                            repository.logAnalyticsEvent(RatingAnalyticsEvent.ReviewLaunched, triggerSource)
                            _result.emit(RatingResult.SuccessReview(stars))
                            _uiState.update { it.copy(isSubmitting = false) }
                            _effect.emit(RatingEffect.ClosePrompt)
                        }
                    }
                )
            } else {
                // Flow B: Redirect to Feedback
                _effect.emit(
                    RatingEffect.RedirectToFeedback(stars) {
                        viewModelScope.launch {
                            repository.recordFeedbackRedirected()
                            repository.logAnalyticsEvent(RatingAnalyticsEvent.FeedbackRedirected, triggerSource)
                            _result.emit(RatingResult.FeedbackRequested(stars))
                            _uiState.update { it.copy(isSubmitting = false) }
                            _effect.emit(RatingEffect.ClosePrompt)
                        }
                    }
                )
            }
        }
    }
}
