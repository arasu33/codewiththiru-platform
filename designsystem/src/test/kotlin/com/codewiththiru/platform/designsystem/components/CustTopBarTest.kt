package com.codewiththiru.platform.designsystem.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTestApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
class CustTopBarTest {
    @Test
    fun topBar_rendersTitleAndSubtitle() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustTopBar(
                        title = { CustText("Main Title") },
                        subtitle = { CustText("Sub Title") },
                        modifier = Modifier.testTag("topBar"),
                    )
                }
            }

            onNodeWithTag("topBar").assertIsDisplayed()
            onNodeWithText("Main Title").assertIsDisplayed()
            onNodeWithText("Sub Title").assertIsDisplayed()
        }

    @Test
    fun topBar_rendersNavigationAndActions() =
        runComposeUiTest {
            var navClicked = false
            var actionClicked = false

            setContent {
                CustTheme {
                    CustTopBar(
                        title = { CustText("Title") },
                        navigationIcon = {
                            CustButton(onClick = { navClicked = true }, modifier = Modifier.testTag("navIcon")) {
                                CustText("Nav")
                            }
                        },
                        actions = {
                            CustButton(onClick = { actionClicked = true }, modifier = Modifier.testTag("actionIcon")) {
                                CustText("Action")
                            }
                        },
                    )
                }
            }

            onNodeWithTag("navIcon")
                .assertIsDisplayed()
                .assertHasClickAction()
                .performClick()
            onNodeWithTag("actionIcon")
                .assertIsDisplayed()
                .assertHasClickAction()
                .performClick()

            assertTrue(navClicked)
            assertTrue(actionClicked)
        }

    @Test
    @Config(qualifiers = "ar-ldrtl")
    fun topBar_rtlSupport() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustTopBar(
                        title = { CustText("RTL Title") },
                        modifier = Modifier.testTag("rtlTopBar"),
                    )
                }
            }
            onNodeWithTag("rtlTopBar").assertIsDisplayed()
            onNodeWithText("RTL Title").assertIsDisplayed()
        }

    @Test
    @Config(qualifiers = "sw600dp")
    fun topBar_tabletSupport() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustCenterAlignedTopBar(
                        title = { CustText("Tablet Title") },
                        modifier = Modifier.testTag("tabletTopBar"),
                    )
                }
            }
            onNodeWithTag("tabletTopBar").assertIsDisplayed()
        }
}
