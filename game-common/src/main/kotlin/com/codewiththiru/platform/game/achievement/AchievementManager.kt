package com.codewiththiru.platform.game.achievement

import kotlinx.serialization.Serializable

@Serializable
enum class AchievementType {
    STANDARD,
    PROGRESSIVE,
    SECRET,
    HIDDEN,
}

@Deprecated("Use AchievementDefinition and AchievementProgress from :game-achievements module instead")
@Serializable
data class Achievement(
    val id: String,
    val name: String,
    val description: String,
    val type: AchievementType = AchievementType.STANDARD,
    val targetProgress: Int = 1,
    val currentProgress: Int = 0,
    val isUnlocked: Boolean = false,
) {
    val isProgressive: Boolean get() = type == AchievementType.PROGRESSIVE
    val isSecret: Boolean get() = type == AchievementType.SECRET || type == AchievementType.HIDDEN
}

@Deprecated("Use AchievementManager from :game-achievements module instead")
interface AchievementManager {
    fun getAchievements(): List<Achievement>

    fun getAchievement(id: String): Achievement?

    fun updateProgress(id: String, progressDelta: Int): Boolean // Returns true if unlocked

    fun unlock(id: String): Boolean

    fun registerAchievements(achievements: List<Achievement>)
}

class DefaultAchievementManager(
    private val saveStorage: com.codewiththiru.platform.game.save.GameStorage,
    private val serializer: com.codewiththiru.platform.game.save.StateSerializer,
    private val onUnlocked: (Achievement) -> Unit = {},
) : AchievementManager {
    private val achievementsKey = "game_achievements_list"

    override fun registerAchievements(achievements: List<Achievement>) {
        if (!saveStorage.hasKey(achievementsKey)) {
            saveList(achievements)
        } else {
            // Merge existing save state with new registered definitions if any
            val existing = getAchievements()
            val merged =
                achievements.map { def ->
                    existing.find { it.id == def.id }?.let { saved ->
                        def.copy(
                            currentProgress = saved.currentProgress,
                            isUnlocked = saved.isUnlocked,
                        )
                    } ?: def
                }
            saveList(merged)
        }
    }

    override fun getAchievements(): List<Achievement> {
        val serialized = saveStorage.getString(achievementsKey) ?: return emptyList()
        return try {
            serializer.deserialize(serialized, kotlin.reflect.typeOf<List<Achievement>>())
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun getAchievement(id: String): Achievement? = getAchievements().find { it.id == id }

    override fun updateProgress(
        id: String,
        progressDelta: Int,
    ): Boolean {
        val currentList = getAchievements().toMutableList()
        val index = currentList.indexOfFirst { it.id == id }
        if (index == -1) return false

        val achievement = currentList[index]
        if (achievement.isUnlocked) return false

        val newProgress = minOf(achievement.targetProgress, achievement.currentProgress + progressDelta)
        val shouldUnlock = newProgress >= achievement.targetProgress

        val updated =
            achievement.copy(
                currentProgress = newProgress,
                isUnlocked = shouldUnlock,
            )
        currentList[index] = updated
        saveList(currentList)

        if (shouldUnlock) {
            onUnlocked(updated)
        }
        return shouldUnlock
    }

    override fun unlock(id: String): Boolean {
        val currentList = getAchievements().toMutableList()
        val index = currentList.indexOfFirst { it.id == id }
        if (index == -1) return false

        val achievement = currentList[index]
        if (achievement.isUnlocked) return false

        val updated =
            achievement.copy(
                currentProgress = achievement.targetProgress,
                isUnlocked = true,
            )
        currentList[index] = updated
        saveList(currentList)

        onUnlocked(updated)
        return true
    }

    private fun saveList(list: List<Achievement>) {
        saveStorage.putString(
            achievementsKey,
            serializer.serialize(list, kotlin.reflect.typeOf<List<Achievement>>()),
        )
    }
}
