package com.codewiththiru.syncbackup.analytics

import com.codewiththiru.platform.analytics.api.AnalyticsManager
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent

class SyncAnalyticsProvider(private val analyticsManager: AnalyticsManager) {
    suspend fun trackSyncCompleted(metrics: SyncMetrics) {
        analyticsManager.track(
            AnalyticsEvent(
                name = "sync_completed",
                params = mapOf(
                    "items_synced" to metrics.itemsSynced.toString(),
                    "duration_ms" to metrics.durationMillis.toString(),
                    "conflicts_resolved" to metrics.conflictsResolved.toString()
                )
            )
        )
    }

    suspend fun trackBackupCompleted(metrics: BackupMetrics) {
        analyticsManager.track(
            AnalyticsEvent(
                name = "backup_completed",
                params = mapOf(
                    "size_bytes" to metrics.sizeBytes.toString(),
                    "duration_ms" to metrics.durationMillis.toString()
                )
            )
        )
    }

    suspend fun trackRestoreCompleted(metrics: RestoreMetrics) {
        analyticsManager.track(
            AnalyticsEvent(
                name = "restore_completed",
                params = mapOf(
                    "items_restored" to metrics.itemsRestored.toString(),
                    "duration_ms" to metrics.durationMillis.toString()
                )
            )
        )
    }
}
