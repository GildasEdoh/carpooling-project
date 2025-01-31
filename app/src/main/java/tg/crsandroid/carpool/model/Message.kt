package tg.crsandroid.carpool.model

import java.util.Date

data class Message(
    val text: String,
    val senderId: String,
    val timestamp: Date? = null,
    val imageUrl: String? = null
)