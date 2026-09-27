package com.codewiththiru.security.storage

/**
 * Interface for hardware-backed, encrypted key-value storage.
 * Designed for sensitive user data, authentication tokens, and encryption secrets.
 */
@Suppress("TooManyFunctions")
interface SecureStorage {
    /** Stores an encrypted string. */
    fun putString(
        key: String,
        value: String,
    )

    /** Retrieves a decrypted string or [defaultValue] if not found. */
    fun getString(
        key: String,
        defaultValue: String? = null,
    ): String?

    /** Stores an encrypted boolean. */
    fun putBoolean(
        key: String,
        value: Boolean,
    )

    /** Retrieves an encrypted boolean or [defaultValue] if not found. */
    fun getBoolean(
        key: String,
        defaultValue: Boolean = false,
    ): Boolean

    /** Stores an encrypted integer. */
    fun putInt(
        key: String,
        value: Int,
    )

    /** Retrieves an encrypted integer or [defaultValue] if not found. */
    fun getInt(
        key: String,
        defaultValue: Int = 0,
    ): Int

    /** Stores an encrypted long. */
    fun putLong(
        key: String,
        value: Long,
    )

    /** Retrieves an encrypted long or [defaultValue] if not found. */
    fun getLong(
        key: String,
        defaultValue: Long = 0L,
    ): Long

    /** Removes a specific key from storage. */
    fun remove(key: String)

    /** Clears all stored keys and values. */
    fun clear()

    /** Checks whether a key exists in storage. */
    fun contains(key: String): Boolean
}
