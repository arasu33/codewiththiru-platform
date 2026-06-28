package com.codewiththiru.platform.game.sync.testing

import com.codewiththiru.platform.game.sync.queue.SyncQueueEngine
import com.codewiththiru.platform.game.sync.queue.SyncRequest
import java.util.LinkedList

class FakeSyncQueueEngine : SyncQueueEngine {
    private val queue = LinkedList<SyncRequest>()

    override fun enqueue(request: SyncRequest) {
        queue.addLast(request)
    }

    override fun peekNext(): SyncRequest? {
        return queue.firstOrNull()
    }

    override fun remove(requestId: String) {
        queue.removeIf { it.id == requestId }
    }

    override fun markFailed(requestId: String) {
        val index = queue.indexOfFirst { it.id == requestId }
        if (index != -1) {
            val req = queue[index]
            queue[index] = req.copy(retryCount = req.retryCount + 1)
        }
    }

    override fun getPendingCount(): Int {
        return queue.size
    }
}
