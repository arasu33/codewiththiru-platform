package com.codewiththiru.remoteconfig.provider.json

import android.content.Context
import com.codewiththiru.remoteconfig.provider.RemoteConfigProvider

class JsonRemoteConfigProvider(
    private val loader: JsonAssetLoader,
    private val parser: JsonParser,
    private val assetFileName: String = "remote_config.json"
) : RemoteConfigProvider {
    
    constructor(context: Context, assetFileName: String = "remote_config.json") : this(
        JsonAssetLoader(context),
        JsonParser(),
        assetFileName
    )

    override val name: String = "JSON"
    
    private var configMap = mutableMapOf<String, Any>()

    override suspend fun initialize() {
        val jsonString = loader.loadStringFromAssets(assetFileName)
        if (jsonString != null) {
            configMap.putAll(parser.parseToMap(jsonString))
        }
    }

    override suspend fun fetch(): Result<Unit> = Result.success(Unit)

    override suspend fun activate(): Boolean = true

    override suspend fun fetchAndActivate(): Result<Boolean> = Result.success(true)

    override fun getString(key: String): String? = configMap[key] as? String
    override fun getBoolean(key: String): Boolean? = configMap[key] as? Boolean
    override fun getInt(key: String): Int? = configMap[key] as? Int
    override fun getLong(key: String): Long? = configMap[key] as? Long
    override fun getDouble(key: String): Double? = configMap[key] as? Double
}
