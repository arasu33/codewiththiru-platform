@file:Suppress("FunctionNaming", "MatchingDeclarationName")

package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.designsystem.components.CustText

enum class LoadingType {
    Circular,
    Linear
}

@Composable
fun CustLoading(
    modifier: Modifier = Modifier,
    message: String? = null,
    type: LoadingType = LoadingType.Circular,
    color: Color = MaterialTheme.colorScheme.primary
) {
    val loadingModifier = modifier.semantics {
        contentDescription = message ?: "Loading"
    }

    if (message != null) {
        if (type == LoadingType.Circular) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = loadingModifier
            ) {
                CircularProgressIndicator(color = color, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(16.dp))
                CustText(text = message)
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = loadingModifier
            ) {
                LinearProgressIndicator(color = color)
                Spacer(modifier = Modifier.height(8.dp))
                CustText(text = message)
            }
        }
    } else {
        if (type == LoadingType.Circular) {
            CircularProgressIndicator(
                color = color,
                modifier = loadingModifier
            )
        } else {
            LinearProgressIndicator(
                color = color,
                modifier = loadingModifier
            )
        }
    }
}
