@file:Suppress("FunctionNaming", "LongMethod")

package com.codewiththiru.platform.about.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.about.AboutDefaults
import com.codewiththiru.platform.about.AboutEventListener
import com.codewiththiru.platform.about.model.AboutConfig
import com.codewiththiru.platform.about.provider.LicenseProvider
import com.codewiththiru.platform.about.state.AboutUiState
import com.codewiththiru.platform.about.ui.components.ActionsSection
import com.codewiththiru.platform.about.ui.components.AppInfoSection
import com.codewiththiru.platform.about.ui.components.DeveloperSection
import com.codewiththiru.platform.about.ui.components.DeviceSection
import com.codewiththiru.platform.about.ui.components.LegalSection
import com.codewiththiru.platform.designsystem.components.CustButton
import com.codewiththiru.platform.designsystem.components.CustCard
import com.codewiththiru.platform.designsystem.components.CustDialog
import com.codewiththiru.platform.designsystem.components.CustText
import com.codewiththiru.platform.designsystem.widgets.CustLoading

/**
 * Turnkey, customizable default About screen.
 */
@Composable
fun DefaultAboutScreen(
    config: AboutConfig,
    eventListener: AboutEventListener = AboutDefaults.eventListener(),
    licenseProvider: LicenseProvider? = null,
    modifier: Modifier = Modifier,
) {
    AboutScreen(
        uiState = AboutUiState.Success(config),
        eventListener = eventListener,
        licenseProvider = licenseProvider,
        modifier = modifier,
    )
}

/**
 * The unified declarative entry point for the About Module.
 */
@Composable
fun AboutScreen(
    uiState: AboutUiState,
    eventListener: AboutEventListener,
    licenseProvider: LicenseProvider? = null,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is AboutUiState.Loading -> {
                CustLoading(
                    modifier = Modifier.align(Alignment.Center),
                )
            }
            is AboutUiState.Error -> {
                CustText(
                    text = uiState.message,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
            is AboutUiState.Success -> {
                AboutScreenContent(
                    config = uiState.config,
                    eventListener = eventListener,
                    licenseProvider = licenseProvider,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun AboutScreenContent(
    config: AboutConfig,
    eventListener: AboutEventListener,
    licenseProvider: LicenseProvider?,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    var showDiagnosticsDialog by remember { mutableStateOf(false) }
    var diagnosticsPayload by remember { mutableStateOf("") }

    if (showDiagnosticsDialog) {
        CustDialog(
            title = { CustText("Diagnostics Payload") },
            content = { CustText(diagnosticsPayload) },
            onDismissRequest = { showDiagnosticsDialog = false },
            confirmButton = {
                CustButton(
                    onClick = {
                        eventListener.onCopyDiagnostics(diagnosticsPayload)
                        showDiagnosticsDialog = false
                    },
                ) {
                    CustText("Copy")
                }
            },
            dismissButton = {
                CustButton(
                    onClick = { showDiagnosticsDialog = false },
                ) {
                    CustText("Cancel")
                }
            },
        )
    }

    Column(
        modifier =
            modifier
                .verticalScroll(scrollState)
                .padding(vertical = 16.dp),
    ) {
        AppInfoSection(
            appInfo = config.appInfo,
            visibility = config.visibility,
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (config.developerInfo != null) {
            CustCard(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
            ) {
                DeveloperSection(
                    developerInfo = config.developerInfo,
                    eventListener = eventListener,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (config.visibility?.showDeviceSection != false && config.deviceInfo != null) {
            CustCard(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
            ) {
                DeviceSection(deviceInfo = config.deviceInfo)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (config.visibility?.showLegalSection != false && config.legalInfo != null) {
            CustCard(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
            ) {
                LegalSection(
                    legalInfo = config.legalInfo,
                    eventListener = eventListener,
                    licenseProvider = licenseProvider,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (config.visibility?.showDiagnostics != false) {
            CustCard(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
            ) {
                ActionsSection(
                    eventListener = eventListener,
                    appInfo = config.appInfo,
                    deviceInfo = config.deviceInfo,
                    developerInfo = config.developerInfo,
                    diagnosticsConfig = config.diagnosticsConfig,
                    onPreviewDiagnostics = { payload ->
                        diagnosticsPayload = payload
                        showDiagnosticsDialog = true
                    },
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
