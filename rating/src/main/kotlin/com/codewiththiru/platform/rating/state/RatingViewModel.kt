package com.codewiththiru.platform.rating.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewiththiru.platform.rating.model.RatingAnalyticsEvent
import com.codewiththiru.platform.rating.model.RatingConfig
import com.codewiththiru.platform.rating.model.RatingResult
import com.codewiththiru.platform.rating.model.RatingTriggerSource
import com.codewiththiru.platform.rating.repository.RatingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RatingViewModel(
    private val config: RatingConfig,
    private val repository: RatingRepository,
    private val triggerSource: RatingTriggerSource? = null,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RatingUiState(promptType = config.promptType))
    val uiState: StateFlow<RatingUiState> = _uiState.asStateFlow()

    private val _effect = kotlinx.coroutines.channels.Channel<RatingEffect>()
    val effect: kotlinx.coroutines.flow.Flow<RatingEffect> = _effect.receiveAsFlow()

    private val _result = kotlinx.coroutines.channels.Channel<RatingResult>()
    val result: kotlinx.coroutines.flow.Flow<RatingResult> = _result.receiveAsFlow()

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
                    mapOf("stars" to action.stars),
                )
            }
            is RatingAction.SubmitClicked -> {
                handleSubmit()
            }
            is RatingAction.DismissClicked -> {
                viewModelScope.launch {
                    repository.logAnalyticsEvent(RatingAnalyticsEvent.Dismissed, triggerSource)
                    _result.send(RatingResult.Dismissed)
                    _effect.send(RatingEffect.ClosePrompt)
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
            mapOf("stars" to stars),
        )

        viewModelScope.launch {
            if (stars >= config.playReviewThreshold) {
                // Flow A: Launch Play Review
                _effect.send(
                    RatingEffect.LaunchPlayReview {
                        viewModelScope.launch {
                            repository.recordReviewLaunched()
                            repository.logAnalyticsEvent(RatingAnalyticsEvent.ReviewLaunched, triggerSource)
                            _result.send(RatingResult.SuccessReview(stars))
                            _uiState.update { it.copy(isSubmitting = false) }
                            _effect.send(RatingEffect.ClosePrompt)
                        }
                    },
                )
            } else {
                // Flow B: Redirect to Feedback
                _effect.send(
                    RatingEffect.RedirectToFeedback(stars) {
                        viewModelScope.launch {
                            repository.recordFeedbackRedirected()
                            repository.logAnalyticsEvent(RatingAnalyticsEvent.FeedbackRedirected, triggerSource)
                            _result.send(RatingResult.FeedbackRequested(stars))
                            _uiState.update { it.copy(isSubmitting = false) }
                            _effect.send(RatingEffect.ClosePrompt)
                        }
                    },
                )
            }
        }
    }
}
