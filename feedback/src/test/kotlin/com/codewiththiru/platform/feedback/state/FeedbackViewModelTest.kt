package com.codewiththiru.platform.feedback.state

import com.codewiththiru.platform.feedback.model.FeedbackCategory
import com.codewiththiru.platform.feedback.model.FeedbackConfig
import com.codewiththiru.platform.feedback.model.FeedbackPayload
import com.codewiththiru.platform.feedback.model.FeedbackSubmissionResult
import com.codewiththiru.platform.feedback.provider.FeedbackSubmissionPolicy
import com.codewiththiru.platform.feedback.provider.FeedbackSubmissionProvider
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class FeedbackViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private val dummyConfig =
        FeedbackConfig
            .Builder()
            .setCategories(listOf(FeedbackCategory("bug", "Bug Report")))
            .build()

    private val dummyPolicy =
        object : FeedbackSubmissionPolicy {
            override fun canSubmit(payload: FeedbackPayload): Boolean = true
        }

    private val dummySubmissionProvider =
        object : FeedbackSubmissionProvider {
            override suspend fun submit(payload: FeedbackPayload): FeedbackSubmissionResult =
                FeedbackSubmissionResult.Success
        }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Idle`() =
        runTest {
            val viewModel = FeedbackViewModel(dummyConfig, dummySubmissionProvider, null, dummyPolicy)
            assertEquals(FeedbackUiState.Idle, viewModel.uiState.value)
        }

    @Test
    fun `action updates form state`() =
        runTest {
            val viewModel = FeedbackViewModel(dummyConfig, dummySubmissionProvider, null, dummyPolicy)
            viewModel.onAction(FeedbackAction.SubjectChanged("New Subject"))
            assertEquals("New Subject", viewModel.formState.value.subject)
        }
}
