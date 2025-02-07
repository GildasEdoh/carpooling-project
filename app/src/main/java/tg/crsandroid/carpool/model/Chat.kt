package tg.crsandroid.carpool.model

import java.util.Date

data class Chat(
    val chatId: String,
    val user1: String,
    val user2: String,
    val lastMessage: String,
    val timestamp: Date
)