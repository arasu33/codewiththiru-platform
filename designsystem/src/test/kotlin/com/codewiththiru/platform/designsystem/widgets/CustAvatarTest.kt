package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
@OptIn(ExperimentalTestApi::class)
class CustAvatarTest {
    @Test
    fun avatar_rendersInitials() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustAvatar(
                        initials = "John Doe",
                        contentDescription = "Profile Avatar",
                    )
                }
            }
            // It should take the first two letters and uppercase them
            onNodeWithText("JO").assertIsDisplayed()
            onNodeWithContentDescription("Profile Avatar").assertIsDisplayed()
        }

    @Test
    fun avatar_rendersPlaceholder() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustAvatar(
                        modifier = Modifier.testTag("placeholderAvatar"),
                        contentDescription = "Empty Avatar",
                    )
                }
            }
            onNodeWithTag("placeholderAvatar").assertIsDisplayed()
            onNodeWithContentDescription("Empty Avatar").assertIsDisplayed()
        }

    @Test
    @Config(qualifiers = "sw600dp")
    fun avatar_tabletSupport() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustAvatar(
                        initials = "TB",
                        size = AvatarSize.Large,
                        modifier = Modifier.testTag("tabletAvatar"),
                    )
                }
            }
            onNodeWithTag("tabletAvatar").assertIsDisplayed()
            onNodeWithText("TB").assertIsDisplayed()
        }
}
