package com.codewiththiru.platform.rating.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.codewiththiru.platform.rating.model.RatingConfig
import com.codewiththiru.platform.rating.model.RatingPromptType
import com.codewiththiru.platform.rating.state.RatingUiState
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
class RatingScreenRoborazziTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val config = RatingConfig.Builder()
        .setPromptType(RatingPromptType.Dialog)
        .build()

    @Test
    fun `capture dialog default state`() {
        composeTestRule.setContent {
            RatingScreen(
                uiState = RatingUiState(selectedStars = 0),
                config = config,
                onAction = {}
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun `capture dialog four stars selected`() {
        composeTestRule.setContent {
            RatingScreen(
                uiState = RatingUiState(selectedStars = 4),
                config = config,
                onAction = {}
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }
}
