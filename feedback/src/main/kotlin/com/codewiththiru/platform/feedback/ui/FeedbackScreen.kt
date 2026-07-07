@file:Suppress("FunctionNaming", "LongParameterList", "UnusedParameter")

package com.codewiththiru.platform.feedback.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.designsystem.components.CustButton
import com.codewiththiru.platform.designsystem.components.CustCard
import com.codewiththiru.platform.designsystem.components.CustTextField
import com.codewiththiru.platform.designsystem.widgets.CustLoading
import com.codewiththiru.platform.feedback.model.FeedbackConfig
import com.codewiththiru.platform.feedback.state.FeedbackAction
import com.codewiththiru.platform.feedback.state.FeedbackFormState
import com.codewiththiru.platform.feedback.state.FeedbackUiState
import com.codewiththiru.platform.feedback.ui.components.CategorySelector
import com.codewiththiru.platform.feedback.ui.components.UserInfoSection

@Suppress("LongParameterList")
@Composable
fun FeedbackScreen(
    uiState: FeedbackUiState,
    formState: FeedbackFormState,
    config: FeedbackConfig,
    onAction: (FeedbackAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        when (uiState) {
            is FeedbackUiState.Submitting -> CustLoading()
            else ->
                FeedbackScreenContent(
                    uiState = uiState,
                    formState = formState,
                    config = config,
                    onAction = onAction,
                )
        }
    }
}

@Suppress("LongParameterList")
@Composable
private fun FeedbackScreenContent(
    uiState: FeedbackUiState,
    formState: FeedbackFormState,
    config: FeedbackConfig,
    onAction: (FeedbackAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    CustCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
        ) {
            if (config.visibility.showCategorySelector) {
                CategorySelector(
                    categories = config.categories,
                    selectedCategory = formState.category,
                    onCategorySelected = { onAction(FeedbackAction.CategorySelected(it)) },
                    themeConfig = config.themeConfig,
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            CustTextField(
                value = formState.subject,
                onValueChange = { onAction(FeedbackAction.SubjectChanged(it)) },
                label = {
                    com.codewiththiru.platform.designsystem.components
                        .CustText("Subject")
                },
                errorText = formState.subjectError,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))

            CustTextField(
                value = formState.description,
                onValueChange = { onAction(FeedbackAction.DescriptionChanged(it)) },
                label = {
                    com.codewiththiru.platform.designsystem.components
                        .CustText("Description")
                },
                errorText = formState.descriptionError,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))

            UserInfoSection(
                name = formState.name,
                onNameChange = { onAction(FeedbackAction.NameChanged(it)) },
                email = formState.email,
                onEmailChange = { onAction(FeedbackAction.EmailChanged(it)) },
                emailError = formState.emailError,
                visibility = config.visibility,
                themeConfig = config.themeConfig,
            )
            Spacer(modifier = Modifier.height(24.dp))

            CustButton(
                onClick = { onAction(FeedbackAction.SubmitClicked) },
                modifier = Modifier.align(Alignment.End),
            ) {
                com.codewiththiru.platform.designsystem.components
                    .CustText("Submit Feedback")
            }
        }
    }
}
