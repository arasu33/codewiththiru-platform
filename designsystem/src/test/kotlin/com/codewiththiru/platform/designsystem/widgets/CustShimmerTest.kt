package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
class CustShimmerTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shimmer_rendersBox() {
        composeTestRule.setContent {
            CustTheme {
                CustShimmerBox(
                    modifier = Modifier.width(100.dp).height(24.dp).testTag("shimmerBox"),
                )
            }
        }
        composeTestRule.onNodeWithTag("shimmerBox").assertIsDisplayed()
    }

    @Test
    fun shimmer_modifierDoesNotCrash() {
        composeTestRule.setContent {
            CustTheme {
                Box(
                    modifier =
                        Modifier
                            .custShimmer()
                            .width(50.dp)
                            .height(50.dp)
                            .testTag("shimmerModifier"),
                )
            }
        }
        composeTestRule.onNodeWithTag("shimmerModifier").assertIsDisplayed()
    }
}
