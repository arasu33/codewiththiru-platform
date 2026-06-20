package com.codewiththiru.remoteconfig.analytics

import android.util.Log

class RemoteConfigLogger {
    fun logEvent(event: String, params: Map<String, Any> = emptyMap()) {
        Log.d("RemoteConfigLogger", "Event: $event | Params: $params")
    }
    
    fun logError(message: String, throwable: Throwable? = null) {
        Log.e("RemoteConfigLogger", message, throwable)
    }
}
