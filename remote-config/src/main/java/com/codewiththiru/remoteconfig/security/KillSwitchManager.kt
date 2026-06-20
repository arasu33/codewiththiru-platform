package com.codewiththiru.remoteconfig.security

interface KillSwitchManager {
    fun isKillSwitchActive(killSwitchKey: String): Boolean
    fun syncKillSwitches(payload: Map<String, Boolean>)
}

class DefaultKillSwitchManager : KillSwitchManager {
    private val activeSwitches = mutableMapOf<String, Boolean>()

    override fun isKillSwitchActive(killSwitchKey: String): Boolean {
        return activeSwitches[killSwitchKey] == true
    }

    override fun syncKillSwitches(payload: Map<String, Boolean>) {
        activeSwitches.clear()
        activeSwitches.putAll(payload)
    }
}
