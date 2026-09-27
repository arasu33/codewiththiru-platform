package com.codewiththiru.widgets

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.codewiththiru.widgets.appbar.PlatformTopAppBar
import com.codewiththiru.widgets.appbar.ThiruTopAppBar
import com.codewiththiru.widgets.state.EmptyStateView
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalTestApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
class WidgetsTest {
    @Test
    fun testPlatformTopAppBarRendersTitleAndSubtitle() =
        runComposeUiTest {
            setContent {
                PlatformTopAppBar(
                    title = "Test Dashboard",
                    subtitle = "Active Overview",
                )
            }

            onNodeWithText("Test Dashboard").assertExists()
            onNodeWithText("Active Overview").assertExists()
        }

    @Suppress("DEPRECATION")
    @Test
    fun testThiruTopAppBarDelegatesCorrectly() =
        runComposeUiTest {
            setContent {
                ThiruTopAppBar(
                    title = "Deprecated Bar",
                    subtitle = "Sub Overview",
                )
            }

            onNodeWithText("Deprecated Bar").assertExists()
            onNodeWithText("Sub Overview").assertExists()
        }

    @Test
    fun testEmptyStateViewRendersCorrectly() =
        runComposeUiTest {
            setContent {
                EmptyStateView(
                    title = "No Items Found",
                    description = "Try adding a new item to get started.",
                    actionLabel = "Add Item",
                    onActionClick = {},
                )
            }

            onNodeWithText("No Items Found").assertExists()
            onNodeWithText("Try adding a new item to get started.").assertExists()
            onNodeWithText("Add Item").assertExists()
        }
}
