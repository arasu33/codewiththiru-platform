package com.codewiththiru.platform.core.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

/**
 * Auto-initializes the CodeWithThiru Platform using AndroidX App Startup.
 * Consumers do NOT need to call an init() method in their Application class.
 * This class is automatically discovered and executed by the manifest merger.
 */
class PlatformInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Log.d("PlatformInitializer", "Auto-initializing CodeWithThiru Platform...")
        // Initialize core dependencies here (e.g., logging, crash reporting, default database instances)
        // If consumer forgot a required config, this is where we throw a clear IllegalStateException:
        // error("Missing API key. Please provide it via codewiththiru-platform.xml or config.")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        // If this platform depends on another initializer (e.g., WorkManagerInitializer), list it here.
        return emptyList()
    }
}
