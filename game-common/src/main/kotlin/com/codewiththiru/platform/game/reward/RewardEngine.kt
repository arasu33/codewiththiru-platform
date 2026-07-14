package com.codewiththiru.platform.game.reward

import kotlinx.serialization.Serializable

@Serializable
enum class RewardType {
    COINS,
    STARS,
    XP,
    DAILY_LOGIN,
    AD_REWARD,
}

@Serializable
data class Reward(
    val id: String,
    val type: RewardType,
    val amount: Long,
    val isClaimed: Boolean = false,
    val claimTimestamp: Long? = null,
)

@Deprecated("Use RewardWallet and RewardBalance from :game-rewards module instead")
@Serializable
data class PlayerWallet(
    val coins: Long = 0,
    val stars: Long = 0,
    val xp: Long = 0,
)

@Deprecated("Use RewardManager from :game-rewards module instead")
interface RewardManager {
    fun getWallet(): PlayerWallet

    fun claimReward(reward: Reward): PlayerWallet

    fun addReward(
        type: RewardType,
        amount: Long,
    ): PlayerWallet
}

class DefaultRewardManager(
    private val saveStorage: com.codewiththiru.platform.game.save.GameStorage,
    private val serializer: com.codewiththiru.platform.game.save.StateSerializer,
    private val onRewardClaimed: (Reward) -> Unit = {},
) : RewardManager {
    private val walletKey = "game_player_wallet"

    override fun getWallet(): PlayerWallet {
        val serialized = saveStorage.getString(walletKey) ?: return PlayerWallet()
        return try {
            serializer.deserialize(serialized, kotlin.reflect.typeOf<PlayerWallet>())
        } catch (e: kotlin.coroutines.cancellation.CancellationException) {
            throw e
        } catch (e: Exception) {
            PlayerWallet()
        }
    }

    override fun claimReward(reward: Reward): PlayerWallet {
        if (reward.isClaimed) return getWallet()

        val wallet = getWallet()
        val updatedWallet =
            when (reward.type) {
                RewardType.COINS -> wallet.copy(coins = wallet.coins + reward.amount)
                RewardType.STARS -> wallet.copy(stars = wallet.stars + reward.amount)
                RewardType.XP -> wallet.copy(xp = wallet.xp + reward.amount)
                else -> wallet // Login/Ad rewards might give coins or XP depending on game rule
            }

        saveWallet(updatedWallet)
        onRewardClaimed(reward.copy(isClaimed = true, claimTimestamp = System.currentTimeMillis()))
        return updatedWallet
    }

    override fun addReward(
        type: RewardType,
        amount: Long,
    ): PlayerWallet {
        val wallet = getWallet()
        val updated =
            when (type) {
                RewardType.COINS -> wallet.copy(coins = wallet.coins + amount)
                RewardType.STARS -> wallet.copy(stars = wallet.stars + amount)
                RewardType.XP -> wallet.copy(xp = wallet.xp + amount)
                else -> wallet
            }
        saveWallet(updated)
        return updated
    }

    private fun saveWallet(wallet: PlayerWallet) {
        saveStorage.putString(
            walletKey,
            serializer.serialize(wallet, kotlin.reflect.typeOf<PlayerWallet>()),
        )
    }
}
