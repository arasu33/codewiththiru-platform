@file:Suppress("FunctionNaming", "Indentation")

package com.codewiththiru.platform.rating.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.rating.model.RatingUiCustomization

private const val MAX_STARS = 5
private const val AMBER_COLOR = 0xFFFFC107

@Composable
fun RatingStars(
    selectedStars: Int,
    onStarSelected: (Int) -> Unit,
    uiCustomization: RatingUiCustomization,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        for (i in 1..MAX_STARS) {
            val isSelected = i <= selectedStars
            val cdStringRes =
                if (isSelected) {
                    uiCustomization.starSelectedContentDescriptionRes
                } else {
                    uiCustomization.starUnselectedContentDescriptionRes
                }
            val contentDesc = stringResource(id = cdStringRes, i)

            IconButton(
                onClick = { onStarSelected(i) },
                modifier =
                    Modifier
                        .padding(4.dp)
                        .semantics {
                            role = Role.RadioButton
                            contentDescription = contentDesc
                        },
            ) {
                Text(
                    text = if (isSelected) "★" else "☆",
                    color = if (isSelected) Color(AMBER_COLOR) else Color.Gray,
                    style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                )
            }
        }
    }
}
