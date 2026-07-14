package com.codewiththiru.notifications.scheduler

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.codewiththiru.notifications.api.NotificationPayload
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class NotificationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val payloadString = inputData.getString("payload") ?: return Result.failure()
        
        return try {
            val payload = Json.decodeFromString<NotificationPayload>(payloadString)
            // Ideally we'd get the provider from DI here and show the notification.
            // For now, we simulate success since the provider isn't injected statically.
            Result.success()
        } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: Exception) {
            Result.failure()
        }
    }
}
