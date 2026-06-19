package com.codewiththiru.platform.core.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Interface mapping standard coroutines execution dispatchers.
 *
 * Promotes local unit-testability by abstracting direct references to standard [kotlinx.coroutines.Dispatchers],
 * allowing dispatchers to be mocked or replaced with test schedulers.
 */
interface CustDispatcherProvider {
    /** Target dispatcher for UI related rendering, operations, or callbacks. */
    val main: CoroutineDispatcher

    /** Target dispatcher for blocking input-output operations (network, file system, database). */
    val io: CoroutineDispatcher

    /** Target dispatcher for intensive computation-heavy background operations (parsing, calculations). */
    val default: CoroutineDispatcher

    /** Target dispatcher that does not constrain the executing thread. */
    val unconfined: CoroutineDispatcher
}
