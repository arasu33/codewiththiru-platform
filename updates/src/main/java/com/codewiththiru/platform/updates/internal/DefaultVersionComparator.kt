package com.codewiththiru.platform.updates.internal

import com.codewiththiru.platform.updates.api.VersionComparator
import kotlin.math.max

class DefaultVersionComparator : VersionComparator {
    override fun compare(
        version1: String,
        version2: String,
    ): Int {
        val parts1 = version1.split(".").map { it.toIntOrNull() ?: 0 }
        val parts2 = version2.split(".").map { it.toIntOrNull() ?: 0 }

        val length = max(parts1.size, parts2.size)
        for (i in 0 until length) {
            val v1 = parts1.getOrElse(i) { 0 }
            val v2 = parts2.getOrElse(i) { 0 }
            if (v1 < v2) return -1
            if (v1 > v2) return 1
        }
        return 0
    }
}
