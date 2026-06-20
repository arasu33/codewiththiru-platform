package com.codewiththiru.platform.feedback.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.codewiththiru.platform.feedback.model.FeedbackCategory
import com.codewiththiru.platform.feedback.model.FeedbackConfig
import com.codewiththiru.platform.feedback.state.FeedbackFormState
import com.codewiththiru.platform.feedback.state.FeedbackUiState
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33], qualifiers = RobolectricDeviceQualifiers.Pixel5)
class FeedbackScreenRoborazziTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val dummyConfig = FeedbackConfig.Builder()
        .setCategories(listOf(FeedbackCategory("bug", "Bug Report")))
        .build()

    @Test
    fun `capture idle state`() {
        composeTestRule.setContent {
            FeedbackScreen(
                uiState = FeedbackUiState.Idle,
                formState = FeedbackFormState(category = dummyConfig.categories.first()),
                config = dummyConfig,
                onAction = {}
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun `capture submitting state`() {
        composeTestRule.setContent {
            FeedbackScreen(
                uiState = FeedbackUiState.Submitting,
                formState = FeedbackFormState(category = dummyConfig.categories.first()),
                config = dummyConfig,
                onAction = {}
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }
}
