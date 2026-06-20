package com.codewiththiru.platform.observability.performance

public enum class StartupType {
    COLD, WARM, HOT
}

public data class StartupMetrics(
    val type: StartupType,
    val durationMs: Long,
    val timeToFirstDrawMs: Long,
    val timeToInteractiveMs: Long
)

public data class FrameMetrics(
    val totalFrames: Int,
    val droppedFrames: Int,
    val jankFrames: Int,
    val slowFrames: Int
)

public data class PerformanceMetric(
    val name: String,
    val durationMs: Long,
    val metadata: Map<String, String> = emptyMap()
)

public data class PerformanceSnapshot(
    val timestamp: Long,
    val cpuUsagePercent: Float,
    val activeThreads: Int
)

public interface PerformanceMonitor {
    public fun startTrace(name: String)
    public fun stopTrace(name: String, metadata: Map<String, String> = emptyMap())
    public fun getStartupMetrics(): StartupMetrics?
    public fun observeFrameMetrics(): kotlinx.coroutines.flow.Flow<FrameMetrics>
}
