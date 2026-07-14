package com.codewiththiru.remoteconfig.provider.json

import android.content.Context

class JsonAssetLoader(private val context: Context) {
    fun loadStringFromAssets(fileName: String): String? {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: Exception) {
            null
        }
    }
}
