package com.codewiththiru.notifications.deepLink

import android.content.Context
import android.content.Intent
import android.net.Uri

interface NotificationRouter {
    fun route(context: Context, deepLink: String)
}

class DeepLinkManager : NotificationRouter {
    override fun route(context: Context, deepLink: String) {
        if (!DeepLinkValidator.isValid(deepLink)) return

        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(deepLink)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        try {
            context.startActivity(intent)
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: kotlin.coroutines.cancellation.CancellationException) {
            throw e
        } catch (e: Exception) {
            // Log analytics failure
        }
    }
}

object DeepLinkValidator {
    @Volatile
    var allowedSchemes: Set<String> = setOf("codewiththiru", "https")

    fun isValid(
        deepLink: String?,
        customSchemes: Set<String>? = null,
    ): Boolean {
        if (deepLink.isNullOrBlank()) return false
        val uri = Uri.parse(deepLink)
        val schemes = customSchemes ?: allowedSchemes
        return uri.scheme != null && uri.scheme!!.lowercase() in schemes
    }
}
