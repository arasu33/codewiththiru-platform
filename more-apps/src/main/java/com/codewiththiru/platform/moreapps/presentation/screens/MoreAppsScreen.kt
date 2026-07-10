@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.codewiththiru.platform.moreapps.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.moreapps.presentation.components.AppCard
import com.codewiththiru.platform.moreapps.presentation.config.MoreAppsConfig
import com.codewiththiru.platform.moreapps.presentation.integration.MoreAppsImageProvider
import com.codewiththiru.platform.moreapps.presentation.integration.MoreAppsNavigationProvider
import com.codewiththiru.platform.moreapps.presentation.state.MoreAppsEffect
import com.codewiththiru.platform.moreapps.presentation.state.MoreAppsUiState
import com.codewiththiru.platform.moreapps.presentation.viewmodel.MoreAppsViewModel

@Suppress("FunctionNaming")
@Composable
fun MoreAppsScreen(
    viewModel: MoreAppsViewModel,
    imageProvider: MoreAppsImageProvider,
    navigationProvider: MoreAppsNavigationProvider,
    config: MoreAppsConfig,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MoreAppsEffect.OpenStore -> navigationProvider.openStore(effect.app)
                is MoreAppsEffect.ShareApp -> navigationProvider.shareApp(effect.app)
                is MoreAppsEffect.NavigateBack -> { /* Handled by Host */ }
                is MoreAppsEffect.ShowError -> { /* Show Toast/Snackbar */ }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val state = uiState) {
            is MoreAppsUiState.Loading -> {
                CircularProgressIndicator()
            }
            is MoreAppsUiState.Empty -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = config.emptyStateConfig.title, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = config.emptyStateConfig.message, style = MaterialTheme.typography.bodyMedium)
                }
            }
            is MoreAppsUiState.Error -> {
                Text(text = "Error: ${state.message}", color = MaterialTheme.colorScheme.error)
            }
            is MoreAppsUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    itemsIndexed(state.apps) { index, app ->
                        AppCard(
                            app = app,
                            imageProvider = imageProvider,
                            onActionClick = { action ->
                                viewModel.onAppAction(app, action, index)
                            },
                        )
                    }
                }
            }
        }
    }
}
