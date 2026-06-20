package com.codewiththiru.remoteconfig.security

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class KillSwitchManagerTest {

    private lateinit var killSwitchManager: DefaultKillSwitchManager

    @Before
    fun setUp() {
        killSwitchManager = DefaultKillSwitchManager()
    }

    @Test
    fun `isKillSwitchActive returns false by default`() {
        assertFalse(killSwitchManager.isKillSwitchActive("disable_ads"))
    }

    @Test
    fun `syncKillSwitches activates kill switches`() {
        val payload = mapOf(
            "disable_ads" to true,
            "disable_billing" to false
        )
        killSwitchManager.syncKillSwitches(payload)

        assertTrue(killSwitchManager.isKillSwitchActive("disable_ads"))
        assertFalse(killSwitchManager.isKillSwitchActive("disable_billing"))
        assertFalse(killSwitchManager.isKillSwitchActive("unknown"))
    }
}
