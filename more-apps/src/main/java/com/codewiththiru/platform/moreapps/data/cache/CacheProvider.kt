package com.codewiththiru.platform.moreapps.data.cache

interface CacheProvider<T> {
    fun get(key: String): T?
    fun put(key: String, value: T)
    fun clear()
}
