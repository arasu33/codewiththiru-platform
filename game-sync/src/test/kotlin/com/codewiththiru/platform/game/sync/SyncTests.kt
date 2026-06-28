package com.codewiththiru.platform.game.sync

import com.codewiththiru.platform.game.sync.conflict.NewestWinsResolver
import com.codewiththiru.platform.game.sync.queue.SyncRequest
import com.codewiththiru.platform.game.sync.testing.FakeSyncQueueEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class SyncTests {
    @Test
    fun `test NewestWinsResolver favors most recent timestamp`() {
        val resolver = NewestWinsResolver()
        val local = """{"coins": 100}"""
        val remote = """{"coins": 50}"""

        // Local is newer
        val result1 = resolver.resolve("profile", local, remote, 2000L, 1000L)
        assertEquals(local, result1)

        // Remote is newer
        val result2 = resolver.resolve("profile", local, remote, 1000L, 2000L)
        assertEquals(remote, result2)
    }

    @Test
    fun `test SyncQueueEngine handles retries properly`() {
        val queue = FakeSyncQueueEngine()
        val request = SyncRequest("req1", "profile", "doc1", "{}", 1000L)

        queue.enqueue(request)
        assertEquals(1, queue.getPendingCount())

        queue.markFailed("req1")
        val peeked = queue.peekNext()
        assertEquals(1, peeked?.retryCount)

        queue.remove("req1")
        assertNull(queue.peekNext())
        assertEquals(0, queue.getPendingCount())
    }
}
