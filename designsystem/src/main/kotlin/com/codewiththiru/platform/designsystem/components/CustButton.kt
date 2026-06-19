@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp

@Composable
fun CustButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    colors: ButtonColors = CustButtonDefaults.buttonColors(),
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = { if (!loading) onClick() },
        modifier = modifier.semantics {
            if (loading) stateDescription = "Loading"
        },
        enabled = enabled,
        colors = colors
    ) {
        ButtonContent(loading = loading, content = content)
    }
}

@Composable
fun CustOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    colors: ButtonColors = CustButtonDefaults.outlinedButtonColors(),
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        onClick = { if (!loading) onClick() },
        modifier = modifier.semantics {
            if (loading) stateDescription = "Loading"
        },
        enabled = enabled,
        colors = colors
    ) {
        ButtonContent(loading = loading, content = content)
    }
}

@Composable
fun CustTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    colors: ButtonColors = CustButtonDefaults.textButtonColors(),
    content: @Composable RowScope.() -> Unit
) {
    TextButton(
        onClick = { if (!loading) onClick() },
        modifier = modifier.semantics {
            if (loading) stateDescription = "Loading"
        },
        enabled = enabled,
        colors = colors
    ) {
        ButtonContent(loading = loading, content = content)
    }
}

@Composable
private fun ButtonContent(
    loading: Boolean,
    content: @Composable RowScope.() -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        val contentAlpha = if (loading) 0f else 1f
        Row(
            modifier = Modifier.alpha(contentAlpha),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp
            )
        }
    }
}
