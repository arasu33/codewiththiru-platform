package com.codewiththiru.platform.core.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Production implementation of [CustDispatcherProvider].
 *
 * Directly delegates to the standard [kotlinx.coroutines.Dispatchers] properties.
 */
class DefaultCustDispatcherProvider : CustDispatcherProvider {
    override val main: CoroutineDispatcher get() = Dispatchers.Main
    override val io: CoroutineDispatcher get() = Dispatchers.IO
    override val default: CoroutineDispatcher get() = Dispatchers.Default
    override val unconfined: CoroutineDispatcher get() = Dispatchers.Unconfined
}
