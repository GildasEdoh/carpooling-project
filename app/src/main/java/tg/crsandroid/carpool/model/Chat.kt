package tg.crsandroid.carpool.model

import java.util.Date

data class Chat(
    val chatId: String,
    val participants: List<String>,
    val lastMessage: String,
    val timestamp: Date?
)