package com.codewiththiru.ads.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codewiththiru.ads.api.AdsEnvironment
import com.codewiththiru.ads.config.AdsConfig
import com.codewiththiru.ads.diagnostics.AdsDiagnostics

/**
 * A debug panel displaying the current state and diagnostics of the Ads Platform.
 * Should only be visible if AdsEnvironment is NOT Production or testMode is true.
 */
@Composable
fun AdsDebugPanel(
    environment: AdsEnvironment,
    config: AdsConfig,
    diagnostics: AdsDiagnostics,
    modifier: Modifier = Modifier
) {
    if (environment == AdsEnvironment.Production && !config.testMode) {
        return // Hidden in production unless testMode is forced
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Ads Debug Panel",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text("Environment: ${environment.name}")
            Text("Test Mode: ${config.testMode}")
            Text("Ads Enabled: ${config.adsEnabled}")
            Text("Consent Status: ${diagnostics.consentStatus}")
            Text("Fill Rate: ${diagnostics.fillRatePercent}%")
            Text("Load Failures: ${diagnostics.loadFailures}")
            // Detailed states could go here
        }
    }
}
