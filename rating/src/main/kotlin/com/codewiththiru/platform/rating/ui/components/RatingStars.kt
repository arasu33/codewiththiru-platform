@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.rating.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
private const val STAR_ICON_SIZE_DP = 48
private const val AMBER_COLOR = 0xFFFFC107

@Composable
fun RatingStars(
    selectedStars: Int,
    onStarSelected: (Int) -> Unit,
    uiCustomization: RatingUiCustomization,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        for (i in 1..MAX_STARS) {
            val isSelected = i <= selectedStars
            val cdStringRes = if (isSelected) {
                uiCustomization.starSelectedContentDescriptionRes
            } else {
                uiCustomization.starUnselectedContentDescriptionRes
            }
            val contentDesc = stringResource(id = cdStringRes, i)

            IconButton(
                onClick = { onStarSelected(i) },
                modifier = Modifier
                    .padding(4.dp)
                    .semantics {
                        role = Role.RadioButton
                        contentDescription = contentDesc
                    }
            ) {
                Icon(
                    imageVector = if (isSelected) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = null, // Handled by modifier semantics
                    tint = if (isSelected) Color(AMBER_COLOR) else Color.Gray,
                    modifier = Modifier.size(STAR_ICON_SIZE_DP.dp)
                )
            }
        }
    }
}
