package com.codewiththiru.platform.gamification.economy

public enum class CurrencyType {
    COIN, GEM
}

public data class CurrencyTransaction(
    val id: String,
    val currency: CurrencyType,
    val amount: Long,
    val reason: String,
    val timestamp: Long
)

public data class Reward(
    val id: String,
    val title: String,
    val costCoins: Long = 0L,
    val costGems: Long = 0L
)

public interface CoinWallet {
    public suspend fun getBalance(userId: String): Long
    public suspend fun addCoins(userId: String, amount: Long, reason: String)
    public suspend fun spendCoins(userId: String, amount: Long, reason: String): Boolean
}

public interface GemWallet {
    public suspend fun getBalance(userId: String): Long
    public suspend fun addGems(userId: String, amount: Long, reason: String)
    public suspend fun spendGems(userId: String, amount: Long, reason: String): Boolean
}

public interface RewardManager {
    public fun getAvailableRewards(): List<Reward>
    public suspend fun purchaseReward(userId: String, rewardId: String): Boolean
}
