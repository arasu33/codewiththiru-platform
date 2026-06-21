package com.codewiththiru.billing.api

sealed class BillingResult<out T> {
    data class Success<T>(val data: T) : BillingResult<T>()

    data class Failure(val error: BillingError, val exception: Throwable? = null) : BillingResult<Nothing>()
}
