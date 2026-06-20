package com.codewiththiru.platform.gamification.badge

public enum class BadgeCategory {
    MILESTONE, EVENT, SPECIAL, RARE
}

public data class Badge(
    val id: String,
    val category: BadgeCategory,
    val name: String,
    val description: String,
    val imageUrl: String
)

public data class BadgeCollection(
    val userId: String,
    val earnedBadges: List<String>
)

public interface BadgeManager {
    public fun getBadgeDefinition(id: String): Badge?
    public suspend fun getCollection(userId: String): BadgeCollection
    public suspend fun awardBadge(userId: String, badgeId: String): Boolean
}
