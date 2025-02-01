package tg.crsandroid.carpool.presentation.screens.chat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tg.crsandroid.carpool.model.Chat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHomeScreen(navController: NavController, userId: String) {
    val db = FirebaseFirestore.getInstance()
    var chats by remember { mutableStateOf<List<Chat>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var participantNames by remember { mutableStateOf<Map<String, String>>(emptyMap()) }

    LaunchedEffect(Unit) {
        val chatRef = db.collection("chats")

        chatRef.get().addOnSuccessListener { querySnapshot ->
            Log.i("ChatHomeScreen", "Récupération des chats réussie. Nombre total: ${querySnapshot.documents.size}")

            val chatList = querySnapshot.documents.mapNotNull { doc ->
                val participants = doc.get("participants") as? List<String> ?: emptyList()
                val lastMessage = doc.getString("lastMessage") ?: "Pas de message"
                val timestamp = doc.getTimestamp("timestamp")?.toDate()

                Log.i("ChatHomeScreen", "Chat trouvé: ${doc.id}, Participants: $participants")

                Chat(
                    chatId = doc.id,
                    participants = participants,
                    lastMessage = lastMessage,
                    timestamp = timestamp
                )
            }

            val userChats = filterChatsByUser(userId, chatList)
            chats = userChats

            Log.i("ChatHomeScreen", "Chats filtrés pour userId=$userId, Nombre: ${userChats.size}")

            fetchParticipantNames(db, userChats, userId) { namesMap ->
                participantNames = namesMap
                Log.i("ChatHomeScreen", "Noms des participants récupérés: $namesMap")
            }

            isLoading = false
        }.addOnFailureListener { e ->
            Log.e("ChatHomeScreen", "Erreur lors de la récupération des chats", e)
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Discussions") },
                Modifier.background(Color(0xFF1976D2))
                //backgroundColor = Color(0xFF1976D2), // Bleu primaire
                //contentColor = Color.White
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = Color.White
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF1976D2))
                }
            } else if (chats.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Aucune conversation",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn {
                    items(chats) { chat ->
                        val otherUserId = chat.participants.firstOrNull { it != userId } ?: ""
                        val otherUserName = participantNames[otherUserId] ?: "Inconnu"

                        Log.i("ChatHomeScreen", "Affichage du chat avec $otherUserId - Nom: $otherUserName")

                        ChatItem(
                            chat = chat,
                            participantName = otherUserName,
                            onClick = {
                                Log.i("ChatHomeScreen", "Navigation vers chat_screen/$userId/$otherUserId")
                                //Navigation vers le chatScreen...
                                //navController.navigate("chat/$userId/$otherUserId")
                            }
                        )
                    }
                }
            }
        }
    }
}

// Composant d'affichage d'un chat
@Composable
fun ChatItem(chat: Chat, participantName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)) // Bleu clair
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Cercle pour l'avatar (placeholder)
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFF1976D2), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = participantName.firstOrNull()?.toString()?.uppercase() ?: "?",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = participantName,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                Text(
                    text = chat.lastMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = formatTimestamp(chat.timestamp),
                style = MaterialTheme.typography.displaySmall,
                color = Color.Gray
            )
        }
    }
}

// Fonction pour filtrer les chats où userId est un participant
fun filterChatsByUser(userId: String, chatList: List<Chat>): List<Chat> {
    return chatList.filter { chat ->
        val isUserInChat = chat.participants.contains(userId)
        Log.i("filterChatsByUser", "Chat ${chat.chatId} - userId=$userId est dedans: $isUserInChat")
        isUserInChat
    }
}

// Fonction pour récupérer les noms des participants
fun fetchParticipantNames(
    db: FirebaseFirestore,
    chats: List<Chat>,
    userId: String,
    onResult: (Map<String, String>) -> Unit
) {
    val otherParticipants = chats.flatMap { chat -> chat.participants }.toSet().filter { it != userId }
    val namesMap = mutableMapOf<String, String>()

    if (otherParticipants.isEmpty()) {
        Log.i("fetchParticipantNames", "Aucun autre participant à récupérer.")
        onResult(namesMap)
        return
    }

    otherParticipants.forEach { participantId ->
        db.collection("users").document(participantId).get()
            .addOnSuccessListener { document ->
                val userName = document.getString("name") ?: "Inconnu"
                namesMap[participantId] = userName
                Log.i("fetchParticipantNames", "Récupéré: $participantId -> $userName")
                onResult(namesMap)
            }
            .addOnFailureListener { e ->
                Log.e("fetchParticipantNames", "Erreur lors de la récupération de $participantId", e)
            }
    }
}
