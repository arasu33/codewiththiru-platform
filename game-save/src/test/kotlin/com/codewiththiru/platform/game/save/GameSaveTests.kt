package com.codewiththiru.platform.game.save

import com.codewiththiru.platform.game.save.api.SaveMetadata
import com.codewiththiru.platform.game.save.api.SaveRequest
import com.codewiththiru.platform.game.save.api.SaveResponse
import com.codewiththiru.platform.game.save.api.SaveSlot
import com.codewiththiru.platform.game.save.testing.FakeGameSaveManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.test.runTest

class GameSaveTests {
    @Test
    fun `test save and load successful`() =
        runTest {
            val manager = FakeGameSaveManager<String>()
            val slot = SaveSlot.manual("slot1")
            val metadata =
                SaveMetadata(
                    timestampMs = 1000L,
                    schemaVersion = 1,
                    playtimeSeconds = 120,
                    gameId = "test_game",
                    gameVersion = "1.0",
                )
            val request = SaveRequest(slot, "game_state_data", metadata)

            val saveResult = manager.save(request)
            assertTrue(saveResult is SaveResponse.Success)
            assertEquals("game_state_data", (saveResult as SaveResponse.Success).state)

            val loadResult = manager.load(slot)
            assertTrue(loadResult is SaveResponse.Success)
            assertEquals("game_state_data", (loadResult as SaveResponse.Success).state)
        }

    @Test
    fun `test save without overwrite fails when exists`() =
        runTest {
            val manager = FakeGameSaveManager<String>()
            val slot = SaveSlot.auto()
            val metadata = SaveMetadata(1L, 1, 10, "test", "1.0")

            val req1 = SaveRequest(slot, "state1", metadata, overwrite = true)
            val req2 = SaveRequest(slot, "state2", metadata, overwrite = false)

            manager.save(req1)
            val result2 = manager.save(req2)

            assertTrue(result2 is SaveResponse.Failure)

            val loadResult = manager.load(slot)
            assertEquals("state1", (loadResult as SaveResponse.Success).state)
        }

    @Test
    fun `test delete save`() =
        runTest {
            val manager = FakeGameSaveManager<String>()
            val slot = SaveSlot.quick()
            val metadata = SaveMetadata(1L, 1, 10, "test", "1.0")

            manager.save(SaveRequest(slot, "state", metadata))
            assertTrue(manager.delete(slot))

            val loadResult = manager.load(slot)
            assertTrue(loadResult is SaveResponse.Failure)
        }
}
