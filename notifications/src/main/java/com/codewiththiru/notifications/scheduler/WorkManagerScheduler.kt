package com.codewiththiru.notifications.scheduler

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.codewiththiru.notifications.api.NotificationPayload
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

class WorkManagerScheduler(private val context: Context) : NotificationScheduler {
    private val workManager = WorkManager.getInstance(context)

    override fun schedule(payload: NotificationPayload, triggerAtMillis: Long) {
        val delay = triggerAtMillis - System.currentTimeMillis()
        if (delay <= 0) return

        val data = Data.Builder()
            .putString("payload", Json.encodeToString(payload))
            .build()

        val request = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        workManager.enqueueUniqueWork(
            payload.id,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    override fun scheduleRepeating(payload: NotificationPayload, intervalMillis: Long) {
        val data = Data.Builder()
            .putString("payload", Json.encodeToString(payload))
            .build()

        val request = PeriodicWorkRequestBuilder<NotificationWorker>(intervalMillis, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        workManager.enqueueUniquePeriodicWork(
            payload.id,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    override fun cancel(notificationId: String) {
        workManager.cancelUniqueWork(notificationId)
    }

    override fun cancelAll() {
        workManager.cancelAllWork()
    }
}
