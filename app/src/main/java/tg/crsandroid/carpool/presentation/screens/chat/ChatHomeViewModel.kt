package tg.crsandroid.carpool.presentation.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tg.crsandroid.carpool.model.Chat
import java.util.Date

class ChatHomeViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> get() = _chats

    fun loadChats(userId: String) {
        viewModelScope.launch {
            // Récupérer les chats où l'utilisateur est un participant
            firestore.collection("chats")
                .whereArrayContains("participants", userId) // Filtre les chats par participant
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        println("Erreur lors de la récupération des chats : $error")
                        return@addSnapshotListener
                    }

                    val chatList = mutableListOf<Chat>()

                    snapshot?.documents?.forEach { document ->
                        val chatId = document.id
                        val participants = document.get("participants") as List<String>
                        val lastMessage = document.getString("lastMessage") ?: ""
                        val timestamp = document.getTimestamp("timestamp")?.toDate() ?: Date()

                        // Trouver l'autre participant
                        val otherUserId = participants.firstOrNull { it != userId } ?: ""

                        chatList.add(
                            Chat(
                                chatId = chatId,
                                user1 = userId,
                                user2 = otherUserId,
                                lastMessage = lastMessage,
                                timestamp = timestamp
                            )
                        )
                    }
                    _chats.value = chatList
                }
        }
    }
}