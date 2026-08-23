package com.codewiththiru.platform.moreapps.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewiththiru.platform.moreapps.domain.model.InstallStatus
import com.codewiththiru.platform.moreapps.domain.model.MoreAppModel
import com.codewiththiru.platform.moreapps.domain.model.MoreAppsResult
import com.codewiththiru.platform.moreapps.domain.repository.MoreAppsRepository
import com.codewiththiru.platform.moreapps.presentation.config.MoreAppsConfig
import com.codewiththiru.platform.moreapps.presentation.integration.AppActionType
import com.codewiththiru.platform.moreapps.presentation.integration.AppInstallResolver
import com.codewiththiru.platform.moreapps.presentation.integration.MoreAppsAnalyticsProvider
import com.codewiththiru.platform.moreapps.presentation.state.MoreAppsEffect
import com.codewiththiru.platform.moreapps.presentation.state.MoreAppsUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoreAppsViewModel(
    private val repository: MoreAppsRepository,
    private val installResolver: AppInstallResolver,
    private val analyticsProvider: MoreAppsAnalyticsProvider,
    private val config: MoreAppsConfig,
) : ViewModel() {
    private val _uiState = MutableStateFlow<MoreAppsUiState>(MoreAppsUiState.Loading)
    val uiState: StateFlow<MoreAppsUiState> = _uiState.asStateFlow()

    private val _effect = kotlinx.coroutines.channels.Channel<MoreAppsEffect>()
    val effect: kotlinx.coroutines.flow.Flow<MoreAppsEffect> = kotlinx.coroutines.flow.receiveAsFlow(_effect)

    init {
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            _uiState.value = MoreAppsUiState.Loading
            when (val result = repository.getApps()) {
                is MoreAppsResult.Success -> {
                    val resolvedApps = result.data.map { resolveInstallStatus(it) }
                    if (resolvedApps.isEmpty()) {
                        _uiState.value = MoreAppsUiState.Empty
                    } else {
                        _uiState.value = MoreAppsUiState.Success(resolvedApps)
                    }
                }
                is MoreAppsResult.Error -> {
                    _uiState.value = MoreAppsUiState.Error(result.throwable.message ?: "Unknown Error")
                }
                is MoreAppsResult.Loading -> {
                    _uiState.value = MoreAppsUiState.Loading
                }
                is MoreAppsResult.Empty -> {
                    _uiState.value = MoreAppsUiState.Empty
                }
            }
        }
    }

    private fun resolveInstallStatus(app: MoreAppModel): MoreAppModel {
        val status =
            when {
                installResolver.isUpdateAvailable(app.packageName) -> InstallStatus.UpdateAvailable
                installResolver.isInstalled(app.packageName) -> InstallStatus.Installed
                else -> InstallStatus.NotInstalled
            }
        return app.copy(installStatus = status)
    }

    fun onAppAction(
        app: MoreAppModel,
        action: AppActionType,
        position: Int,
    ) {
        analyticsProvider.logAppAction(app, action, position, "MainList")
        viewModelScope.launch {
            when (action) {
                AppActionType.Install, AppActionType.Open -> _effect.send(MoreAppsEffect.OpenStore(app))
                AppActionType.Share -> _effect.send(MoreAppsEffect.ShareApp(app))
                AppActionType.View -> _effect.send(MoreAppsEffect.OpenStore(app))
            }
        }
    }
}
