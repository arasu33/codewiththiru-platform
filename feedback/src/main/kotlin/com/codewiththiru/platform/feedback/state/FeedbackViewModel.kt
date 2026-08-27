package com.codewiththiru.platform.feedback.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewiththiru.platform.feedback.model.FeedbackConfig
import com.codewiththiru.platform.feedback.model.FeedbackPayload
import com.codewiththiru.platform.feedback.model.FeedbackSubmissionResult
import com.codewiththiru.platform.feedback.provider.FeedbackDraftProvider
import com.codewiththiru.platform.feedback.provider.FeedbackSubmissionPolicy
import com.codewiththiru.platform.feedback.provider.FeedbackSubmissionProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FeedbackViewModel(
    private val config: FeedbackConfig,
    private val submissionProvider: FeedbackSubmissionProvider,
    private val draftProvider: FeedbackDraftProvider?,
    private val submissionPolicy: FeedbackSubmissionPolicy,
) : ViewModel() {
    private val _uiState = MutableStateFlow<FeedbackUiState>(FeedbackUiState.Idle)
    val uiState: StateFlow<FeedbackUiState> = _uiState.asStateFlow()

    private val _formState = MutableStateFlow(FeedbackFormState(category = config.categories.firstOrNull()))
    val formState: StateFlow<FeedbackFormState> = _formState.asStateFlow()

    private val _effect = kotlinx.coroutines.channels.Channel<FeedbackEffect>()
    val effect: kotlinx.coroutines.flow.Flow<FeedbackEffect> = _effect.receiveAsFlow()

    init {
        loadDraft()
    }

    private var saveDraftJob: kotlinx.coroutines.Job? = null

    fun onAction(action: FeedbackAction) {
        when (action) {
            is FeedbackAction.CategorySelected -> _formState.update { it.copy(category = action.category) }
            is FeedbackAction.SubjectChanged ->
                _formState.update {
                    it.copy(subject = action.subject, subjectError = null)
                }
            is FeedbackAction.DescriptionChanged ->
                _formState.update {
                    it.copy(description = action.description, descriptionError = null)
                }
            is FeedbackAction.EmailChanged ->
                _formState.update {
                    it.copy(email = action.email, emailError = null)
                }
            is FeedbackAction.NameChanged -> _formState.update { it.copy(name = action.name) }
            is FeedbackAction.AttachmentAdded -> { /* Handle Attachment */ }
            is FeedbackAction.AttachmentRemoved -> { /* Handle Attachment */ }
            is FeedbackAction.SubmitClicked -> submitFeedback()
            is FeedbackAction.DismissErrorClicked -> _uiState.value = FeedbackUiState.Idle
        }

        // Auto-save draft on every key action if configured
        val skipSave = action is FeedbackAction.SubmitClicked || action is FeedbackAction.DismissErrorClicked
        if (config.offlineConfig.autoSaveDrafts && !skipSave) {
            saveDraftJob?.cancel()
            saveDraftJob = viewModelScope.launch {
                kotlinx.coroutines.delay(1000)
                saveDraft()
            }
        }
    }

    private fun loadDraft() {
        if (draftProvider == null) return
        viewModelScope.launch {
            val draft = draftProvider.loadDraft()
            if (draft != null) {
                _formState.update {
                    it.copy(
                        category = draft.category,
                        subject = draft.subject,
                        description = draft.description,
                        email = draft.userEmail ?: "",
                        name = draft.userName ?: "",
                    )
                }
            }
        }
    }

    private fun saveDraft() {
        if (draftProvider == null) return
        val currentForm = _formState.value
        val cat = currentForm.category ?: return
        viewModelScope.launch {
            draftProvider.saveDraft(
                FeedbackPayload(
                    category = cat,
                    subject = currentForm.subject,
                    description = currentForm.description,
                    userEmail = currentForm.email.takeIf { it.isNotBlank() },
                    userName = currentForm.name.takeIf { it.isNotBlank() },
                    attachments = emptyList(),
                ),
            )
        }
    }

    private fun validateForm(): Boolean {
        val form = _formState.value
        var isValid = true
        var subjectError: String? = null
        var descError: String? = null
        var emailError: String? = null

        if (form.subject.isBlank() || form.subject.length > config.validationConfig.maxSubjectLength) {
            subjectError = "Invalid subject length"
            isValid = false
        }
        if (form.description.isBlank() || form.description.length > config.validationConfig.maxDescriptionLength) {
            descError = "Invalid description length"
            isValid = false
        }
        if (config.visibility.requireEmail && !config.validationConfig.emailRegex.matches(form.email)) {
            emailError = "Invalid email format"
            isValid = false
        }

        if (!isValid) {
            _formState.update {
                it.copy(subjectError = subjectError, descriptionError = descError, emailError = emailError)
            }
        }
        return isValid
    }

    @Suppress("ReturnCount")
    private fun submitFeedback() {
        if (!validateForm()) return

        val form = _formState.value
        val cat = form.category ?: return

        val payload =
            FeedbackPayload(
                category = cat,
                subject = form.subject,
                description = form.description,
                userEmail = form.email.takeIf { it.isNotBlank() },
                userName = form.name.takeIf { it.isNotBlank() },
                attachments = emptyList(), // To be handled
            )

        if (!submissionPolicy.canSubmit(payload)) {
            _uiState.value = FeedbackUiState.Error("Submission not allowed by policy")
            return
        }

        _uiState.value = FeedbackUiState.Submitting
        viewModelScope.launch {
            when (val result = submissionProvider.submit(payload)) {
                is FeedbackSubmissionResult.Success -> {
                    _uiState.value = FeedbackUiState.Success("Feedback submitted successfully")
                    if (config.offlineConfig.clearDraftOnSuccess) {
                        draftProvider?.clearDraft()
                    }
                    _effect.send(FeedbackEffect.ShowToast("Feedback Submitted"))
                    _effect.send(FeedbackEffect.NavigateBack)
                }
                is FeedbackSubmissionResult.NetworkError -> {
                    _uiState.value = FeedbackUiState.Error("Network error. Retry available: ${result.retryAvailable}")
                }
                is FeedbackSubmissionResult.ValidationError -> {
                    _uiState.value = FeedbackUiState.Error("Validation Error: ${result.message}")
                }
                is FeedbackSubmissionResult.UnknownError -> {
                    _uiState.value = FeedbackUiState.Error("Unknown error occurred")
                }
            }
        }
    }
}
