package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
@OptIn(ExperimentalTestApi::class)
class CustShimmerTest {
    @Test
    fun shimmer_rendersBox() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustShimmerBox(
                        modifier = Modifier.width(100.dp).height(24.dp).testTag("shimmerBox"),
                    )
                }
            }
            onNodeWithTag("shimmerBox").assertIsDisplayed()
        }

    @Test
    fun shimmer_modifierDoesNotCrash() =
        runComposeUiTest {
            setContent {
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
            onNodeWithTag("shimmerModifier").assertIsDisplayed()
        }
}
