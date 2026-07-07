@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.feedback.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.designsystem.components.CustText
import com.codewiththiru.platform.feedback.model.FeedbackCategory
import com.codewiththiru.platform.feedback.model.FeedbackThemeConfig

@Composable
internal fun CategorySelector(
    categories: List<FeedbackCategory>,
    selectedCategory: FeedbackCategory?,
    onCategorySelected: (FeedbackCategory) -> Unit,
    themeConfig: FeedbackThemeConfig,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        CustText(
            text = "Category",
            style = themeConfig.labelStyle ?: androidx.compose.ui.text.TextStyle.Default,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        CustText(
            text = selectedCategory?.displayName ?: "Select Category",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .semantics {
                        role = Role.DropdownList
                        contentDescription = "Select feedback category. " +
                            "Current selection: ${selectedCategory?.displayName ?: "None"}"
                    }.clickable { expanded = true }
                    .padding(16.dp),
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { CustText(text = category.displayName) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    },
                )
            }
        }
    }
}
