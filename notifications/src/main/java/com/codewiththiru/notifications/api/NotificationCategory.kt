package com.codewiththiru.notifications.api

enum class NotificationPriority {
    MIN, LOW, DEFAULT, HIGH, MAX
}

enum class NotificationCategory(val channelId: String, val channelName: String, val channelDesc: String) {
    GENERAL("channel_general", "General", "General app notifications"),
    PROMOTIONS("channel_promotions", "Promotions", "Offers, discounts, and campaigns"),
    LEARNING_REMINDERS("channel_learning", "Learning Reminders", "Reminders to keep your streak alive"),
    GAMES("channel_games", "Games", "Sudoku, Chess, and Number Match updates"),
    SYSTEM("channel_system", "System", "Important account and system alerts")
}
