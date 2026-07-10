@file:Suppress("FunctionNaming", "Indentation")

package com.codewiththiru.platform.rating.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.designsystem.components.CustButton
import com.codewiththiru.platform.designsystem.components.CustCard
import com.codewiththiru.platform.designsystem.components.CustText
import com.codewiththiru.platform.designsystem.components.CustTextButton
import com.codewiththiru.platform.rating.model.RatingUiCustomization
import com.codewiththiru.platform.rating.state.RatingAction

@Composable
fun RatingDialog(
    selectedStars: Int,
    isSubmitting: Boolean,
    uiCustomization: RatingUiCustomization,
    onAction: (RatingAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    CustCard(modifier = modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
        ) {
            CustText(
                text = stringResource(id = uiCustomization.titleRes),
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustText(
                text = stringResource(id = uiCustomization.messageRes),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(24.dp))

            RatingStars(
                selectedStars = selectedStars,
                onStarSelected = { onAction(RatingAction.StarSelected(it)) },
                uiCustomization = uiCustomization,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                CustTextButton(
                    onClick = { onAction(RatingAction.DismissClicked) },
                    enabled = !isSubmitting,
                ) {
                    CustText(stringResource(id = uiCustomization.negativeButtonTextRes))
                }
                CustButton(
                    onClick = { onAction(RatingAction.SubmitClicked) },
                    enabled = selectedStars > 0 && !isSubmitting,
                    modifier = Modifier.padding(start = 8.dp),
                ) {
                    CustText(stringResource(id = uiCustomization.positiveButtonTextRes))
                }
            }
        }
    }
}
