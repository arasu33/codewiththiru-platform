package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
@OptIn(ExperimentalTestApi::class)
class CustDividerTest {
    @Test
    fun horizontalDivider_renders() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustHorizontalDivider(modifier = Modifier.testTag("hDivider"))
                }
            }
            onNodeWithTag("hDivider").assertIsDisplayed()
        }

    @Test
    fun verticalDivider_renders() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustVerticalDivider(modifier = Modifier.testTag("vDivider"))
                }
            }
            onNodeWithTag("vDivider").assertIsDisplayed()
        }
}
