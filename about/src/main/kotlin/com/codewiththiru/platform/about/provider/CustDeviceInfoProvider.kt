package com.codewiththiru.platform.about.provider

import com.codewiththiru.platform.about.model.DeviceInfo

/**
 * Abstraction to provide generic telemetry mappings disconnected from explicit os dependencies.
 */
interface CustDeviceInfoProvider {
    fun provideDeviceInfo(): DeviceInfo
}
