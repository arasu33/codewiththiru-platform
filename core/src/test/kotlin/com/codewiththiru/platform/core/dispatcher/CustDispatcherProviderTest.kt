package com.codewiththiru.platform.core.dispatcher

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher

class CustDispatcherProviderTest {
    @Test
    fun testDefaultDispatcherProviderDelegation() {
        val provider: CustDispatcherProvider = DefaultCustDispatcherProvider()

        assertEquals(Dispatchers.Main, provider.main)
        assertEquals(Dispatchers.IO, provider.io)
        assertEquals(Dispatchers.Default, provider.default)
        assertEquals(Dispatchers.Unconfined, provider.unconfined)
    }

    @Test
    fun testMockDispatcherProviderSwap() {
        val testDispatcher = StandardTestDispatcher()

        val mockProvider =
            object : CustDispatcherProvider {
                override val main: CoroutineDispatcher = testDispatcher
                override val io: CoroutineDispatcher = testDispatcher
                override val default: CoroutineDispatcher = testDispatcher
                override val unconfined: CoroutineDispatcher = testDispatcher
            }

        assertEquals(testDispatcher, mockProvider.main)
        assertEquals(testDispatcher, mockProvider.io)
        assertEquals(testDispatcher, mockProvider.default)
        assertEquals(testDispatcher, mockProvider.unconfined)
    }
}
