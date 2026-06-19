@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.codewiththiru.platform.designsystem.components.CustText

@Composable
fun CustBadge(
    count: Int,
    modifier: Modifier = Modifier,
    maxCount: Int = 99,
    content: @Composable () -> Unit
) {
    val displayCount = if (count > maxCount) "$maxCount+" else count.toString()
    val description = "$count notifications"

    BadgedBox(
        modifier = modifier.semantics {
            contentDescription = description
        },
        badge = {
            if (count > 0) {
                Badge(
                    containerColor = CustBadgeDefaults.containerColor,
                    contentColor = CustBadgeDefaults.contentColor
                ) {
                    CustText(
                        text = displayCount,
                        style = CustBadgeDefaults.textStyle
                    )
                }
            }
        }
    ) {
        content()
    }
}
