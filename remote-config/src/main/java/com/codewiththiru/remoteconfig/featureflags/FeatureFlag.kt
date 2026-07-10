package com.codewiththiru.remoteconfig.featureflags

data class FeatureFlagGroup(
    val id: String,
    val description: String = ""
)

data class FeatureFlag(
    val key: String,
    val group: FeatureFlagGroup,
    val enabled: Boolean,
    val rolloutPercentage: Int = 100,
    val userSegments: List<String> = emptyList(),
    val environmentOverrides: Map<String, Boolean> = emptyMap()
)

interface FeatureFlagManager {
    fun isEnabled(flagKey: String, userContext: Map<String, String> = emptyMap()): Boolean
    fun getFlag(flagKey: String): FeatureFlag?
    fun syncFlags(flags: List<FeatureFlag>)
}

class DefaultFeatureFlagManager : FeatureFlagManager {
    private val flagsMap = java.util.concurrent.ConcurrentHashMap<String, FeatureFlag>()

    override fun isEnabled(flagKey: String, userContext: Map<String, String>): Boolean {
        val flag = flagsMap[flagKey] ?: return false
        
        // Environment override logic could go here based on userContext

        if (!flag.enabled) return false

        // User segments logic
        if (flag.userSegments.isNotEmpty() && userContext.isNotEmpty()) {
            val userSegment = userContext["segment"]
            if (userSegment != null && !flag.userSegments.contains(userSegment)) {
                return false
            }
        }

        // Rollout percentage logic
        // This would ideally use a consistent hashing of a user ID to evaluate
        val hash = (userContext["userId"] ?: "anonymous").hashCode() % 100
        val normalizedHash = if (hash < 0) hash + 100 else hash

        return normalizedHash < flag.rolloutPercentage
    }

    override fun getFlag(flagKey: String): FeatureFlag? = flagsMap[flagKey]

    override fun syncFlags(flags: List<FeatureFlag>) {
        flagsMap.clear()
        flags.forEach { flagsMap[it.key] = it }
    }
}
