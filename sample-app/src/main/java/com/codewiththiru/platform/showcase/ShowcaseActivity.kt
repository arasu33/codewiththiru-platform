package com.codewiththiru.platform.showcase

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.showcase.screens.*

class ShowcaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Platform Managers here
    }
}

class ShowcaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShowcaseNavigator()
                }
            }
        }
    }
}

enum class Screen {
    DASHBOARD,
    ANALYTICS, REMOTE_CONFIG, ADS,
    FEEDBACK, RATING, UPDATES, MORE_APPS,
    SECURITY, IDENTITY, OBSERVABILITY, NOTIFICATIONS,
    AI, DEVELOPER_TOOLS, HEALTH_CENTER,
    BILLING, COUPONS, GAMIFICATION
}

@Composable
fun ShowcaseNavigator() {
    var currentScreen by remember { mutableStateOf(Screen.DASHBOARD) }

    if (currentScreen == Screen.DASHBOARD) {
        DashboardScreen(onNavigate = { currentScreen = it })
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            Button(onClick = { currentScreen = Screen.DASHBOARD }, modifier = Modifier.padding(16.dp)) {
                Text("Back to Dashboard")
            }
            Surface(modifier = Modifier.weight(1f).padding(16.dp)) {
                when (currentScreen) {
                    Screen.ANALYTICS -> AnalyticsScreen()
                    Screen.REMOTE_CONFIG -> RemoteConfigScreen()
                    Screen.ADS -> AdsScreen()
                    Screen.FEEDBACK -> FeedbackScreen()
                    Screen.RATING -> RatingScreen()
                    Screen.UPDATES -> UpdatesScreen()
                    Screen.MORE_APPS -> MoreAppsScreen()
                    Screen.SECURITY -> SecurityScreen()
                    Screen.IDENTITY -> IdentityScreen()
                    Screen.OBSERVABILITY -> ObservabilityScreen()
                    Screen.NOTIFICATIONS -> NotificationsScreen()
                    Screen.AI -> AIScreen()
                    Screen.DEVELOPER_TOOLS -> DeveloperToolsScreen()
                    Screen.HEALTH_CENTER -> HealthCenterScreen()
                    Screen.BILLING -> BillingScreen()
                    Screen.COUPONS -> CouponsScreen()
                    Screen.GAMIFICATION -> GamificationScreen()
                    else -> {}
                }
            }
        }
    }
}

@Composable
fun DashboardScreen(onNavigate: (Screen) -> Unit) {
    val screens = Screen.values().filter { it != Screen.DASHBOARD }
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item { Text("CodeWithThiru Platform Showcase", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp)) }
        items(screens) { screen ->
            Button(
                onClick = { onNavigate(screen) },
                modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()
            ) {
                Text(screen.name)
            }
        }
    }
}
