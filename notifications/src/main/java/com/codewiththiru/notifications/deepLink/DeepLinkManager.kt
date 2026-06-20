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
        
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deepLink)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            // Log analytics failure
        }
    }
}

object DeepLinkValidator {
    fun isValid(deepLink: String?): Boolean {
        if (deepLink.isNullOrBlank()) return false
        val uri = Uri.parse(deepLink)
        return uri.scheme == "codewiththiru" || uri.scheme == "https"
    }
}
