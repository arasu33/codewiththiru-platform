package com.codewiththiru.platform.showcase.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.showcase.di.ShowcaseDI

@Composable
fun AnalyticsScreen() {
    val events by ShowcaseDI.analyticsManager.events.collectAsState()
    
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Analytics Showcase", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Events Queued: ${events.size}")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { ShowcaseDI.analyticsManager.trackEvent("SampleEvent_${events.size}") }) {
            Text("Track Event")
        }
        Button(onClick = { ShowcaseDI.analyticsManager.flush() }) {
            Text("Flush Events")
        }
    }
}

@Composable
fun RemoteConfigScreen() {
    val config by ShowcaseDI.remoteConfigManager.config.collectAsState()
    
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Remote Config Showcase", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        config.forEach { (key, value) ->
            Text("$key: $value")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { ShowcaseDI.remoteConfigManager.fetch() }) {
            Text("Force Fetch")
        }
    }
}

@Composable
fun AdsScreen() {
    val loaded by ShowcaseDI.adsManager.adsLoaded.collectAsState()
    
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Ads Showcase", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Ad Loaded: $loaded")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { ShowcaseDI.adsManager.loadAd() }) {
            Text("Load Ad")
        }
        Button(onClick = { ShowcaseDI.adsManager.showAd() }, enabled = loaded) {
            Text("Show Ad")
        }
    }
}
