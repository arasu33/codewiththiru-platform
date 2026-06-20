package com.codewiththiru.platform.observability.memory

public data class MemorySnapshot(
    val timestamp: Long,
    val totalRamMb: Long,
    val availableRamMb: Long,
    val heapAllocatedMb: Long,
    val heapMaxMb: Long
)

public data class ANREvent(
    val timestamp: Long,
    val blockDurationMs: Long,
    val stackTrace: String,
    val threadName: String
)

public interface MemoryLeakDetector {
    public fun watch(obj: Any, description: String)
    public fun dumpHeap()
}

public interface ANRMonitor {
    public fun startMonitoring()
    public fun stopMonitoring()
}

public interface MemoryMonitor {
    public fun captureSnapshot(): MemorySnapshot
    public fun isLowMemory(): Boolean
}
