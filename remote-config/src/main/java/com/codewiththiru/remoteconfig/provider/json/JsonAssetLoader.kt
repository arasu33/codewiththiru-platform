package com.codewiththiru.remoteconfig.provider.json

import android.content.Context

class JsonAssetLoader(private val context: Context) {
    fun loadStringFromAssets(fileName: String): String? {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            null
        }
    }
}
