package com.codewiththiru.platform.core.result

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A sealed interface representing the result of an operation that could succeed or fail.
 *
 * Encapsulates success with data [Success] or failure with an exception [Failure].
 *
 * @param T The type of the value wrapped inside [Success].
 */
sealed interface CustResult<out T> {
    /**
     * Represents a successful execution wrapping the returned value.
     *
     * @property value The output data returned on success.
     */
    data class Success<out T>(
        val value: T,
    ) : CustResult<T>

    /**
     * Represents a failed execution wrapping the throw error.
     *
     * @property exception The exception thrown on failure.
     */
    data class Failure(
        val exception: Throwable,
    ) : CustResult<Nothing>
}

/**
 * Transforms the outcome of the [CustResult] by executing [onSuccess] or [onFailure].
 *
 * @param onSuccess Callback executed with the successful value.
 * @param onFailure Callback executed with the failure exception.
 * @return The result computed by the executed lambda block.
 */
@OptIn(ExperimentalContracts::class)
inline fun <T, R> CustResult<T>.fold(
    onSuccess: (T) -> R,
    onFailure: (Throwable) -> R,
): R {
    contract {
        callsInPlace(onSuccess, InvocationKind.AT_MOST_ONCE)
        callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is CustResult.Success -> onSuccess(value)
        is CustResult.Failure -> onFailure(exception)
    }
}

/**
 * Maps the successful value inside the [CustResult] using the [transform] lambda.
 *
 * Returns [CustResult.Failure] as is.
 *
 * @param transform Callback to map the success value.
 * @return A new [CustResult] wrapping the mapped value.
 */
@OptIn(ExperimentalContracts::class)
inline fun <T, R> CustResult<T>.map(transform: (T) -> R): CustResult<R> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is CustResult.Success -> CustResult.Success(transform(value))
        is CustResult.Failure -> this
    }
}

/**
 * Maps the successful value inside the [CustResult] to another [CustResult] using [transform].
 *
 * Returns [CustResult.Failure] as is.
 *
 * @param transform Callback to flatMap the success value.
 * @return A new [CustResult] containing the transformed result.
 */
@OptIn(ExperimentalContracts::class)
inline fun <T, R> CustResult<T>.flatMap(transform: (T) -> CustResult<R>): CustResult<R> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is CustResult.Success -> transform(value)
        is CustResult.Failure -> this
    }
}

/**
 * Recovers from a failure by transforming the thrown exception into a fallback success value.
 *
 * Returns [CustResult.Success] as is.
 *
 * @param transform Callback mapping the exception to a success value.
 * @return A successful [CustResult] wrapping the fallback value.
 */
@OptIn(ExperimentalContracts::class)
inline fun <T> CustResult<T>.recover(transform: (Throwable) -> T): CustResult<T> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is CustResult.Success -> this
        is CustResult.Failure -> CustResult.Success(transform(exception))
    }
}

/**
 * Executes the [action] block if the [CustResult] is a [CustResult.Success].
 *
 * @param action Callback executed on successful outcomes.
 * @return The original [CustResult] unmodified (enabling chaining).
 */
@OptIn(ExperimentalContracts::class)
inline fun <T> CustResult<T>.onSuccess(action: (T) -> Unit): CustResult<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is CustResult.Success) action(value)
    return this
}

/**
 * Executes the [action] block if the [CustResult] is a [CustResult.Failure].
 *
 * @param action Callback executed on failure outcomes.
 * @return The original [CustResult] unmodified (enabling chaining).
 */
@OptIn(ExperimentalContracts::class)
inline fun <T> CustResult<T>.onFailure(action: (Throwable) -> Unit): CustResult<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is CustResult.Failure) action(exception)
    return this
}

/**
 * Extracts the success value, or returns the result of [onFailure] if it's a failure.
 *
 * @param onFailure Callback executed to handle the failure case.
 * @return The successful value or the output computed by the callback.
 */
@OptIn(ExperimentalContracts::class)
inline fun <T, R : T> CustResult<T>.getOrElse(onFailure: (Throwable) -> R): T {
    contract {
        callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is CustResult.Success -> value
        is CustResult.Failure -> onFailure(exception)
    }
}

/**
 * Executes the throwing [block] parameter inside a try-catch block and wraps it in a [CustResult].
 *
 * Note: Fatal JVM errors (such as [VirtualMachineError], [OutOfMemoryError], and [StackOverflowError])
 * and coroutine cancellation ([kotlin.coroutines.cancellation.CancellationException]) will NOT be caught
 * to preserve compiler, runtime, and coroutine execution stability.
 *
 * @param block The code to execute safely.
 * @return A [CustResult.Success] containing the output, or [CustResult.Failure] with the thrown exception.
 */
@Suppress("TooGenericExceptionCaught", "InstanceOfCheckForException", "ThrowsCount")
inline fun <T> custRunCatching(block: () -> T): CustResult<T> =
    try {
        CustResult.Success(block())
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: VirtualMachineError) {
        throw e
    } catch (e: Exception) {
        CustResult.Failure(e)
    }
