package tg.crsandroid.carpool.presentation.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import tg.crsandroid.carpool.model.Message
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, userIdX: String?, userIdY: String?) {
    val db = FirebaseFirestore.getInstance()
    val messagesCollection = db.collection("c")
    var messages by remember { mutableStateOf<List<Message>>(emptyList()) }
    var newMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    val listState = rememberLazyListState()
    var userName by remember { mutableStateOf("") }

    LaunchedEffect(userIdY) {
        if (userIdY != null) {
            db.collection("users")
                .document(userIdY)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        userName = document.getString("name") ?: "Unknown User"
                    }
                }
                .addOnFailureListener { e ->
                    println("Erreur lors de la récupération du nom de l'utilisateur : $e")
                }
        }
    }

    if (userIdX != null && userIdY != null) {
        val chatId = getChatId(userIdX, userIdY)

        DisposableEffect(chatId) {
            val query = db.collection("chats")
                .document(chatId)
                .collection("messages")
                .orderBy("timestamp")

            val listener = query.addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    println("Erreur lors de la récupération des messages : $error")
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    val messageList = querySnapshot.documents.mapNotNull { doc ->
                        val text = doc.getString("text") ?: ""
                        val senderId = doc.getString("senderId") ?: ""
                        val timestamp = doc.getTimestamp("timestamp")?.toDate()
                        val imageUrl = doc.getString("imageUrl")
                        Message(text, senderId, timestamp, imageUrl)
                    }
                    messages = messageList
                    isLoading = false
                }
            }

            onDispose {
                listener.remove()
            }
        }
    }

    LaunchedEffect(messages) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    fun sendMessage() {
        if (newMessage.isNotBlank() && userIdX != null && userIdY != null) {
            val chatId = getChatId(userIdX, userIdY)
            val messageData = hashMapOf(
                "text" to newMessage,
                "senderId" to userIdX,
                "timestamp" to Timestamp.now()
            )

            db.collection("chats")
                .document(chatId)
                .set(
                    mapOf(
                        "participants" to listOf(userIdX, userIdY),
                        "lastMessage" to newMessage,
                        "timestamp" to Timestamp.now()
                    ), SetOptions.merge()
                )

            db.collection("chats")
                .document(chatId)
                .collection("messages")
                .add(messageData)
                .addOnSuccessListener {
                    newMessage = ""
                }
                .addOnFailureListener { e ->
                    println("Erreur lors de l'envoi du message : $e")
                }

        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF3C52C5))
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                    println("Retour en arrière")
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Retour",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                ProfileIcon(userName)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (messages.isEmpty()) {
                Text(
                    text = "No messages yet",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    state = listState
                ) {
                    items(messages) { message ->
                        MessageItem(message, userIdX)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = newMessage,
                    onValueChange = { newMessage = it },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    placeholder = { Text("Entrez votre message...") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF0F8FF)
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { sendMessage() },
                    modifier = Modifier.fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(Color(0xFF4169E1))
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Envoyer",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileIcon(userName: String) {
    val firstLetter = userName.take(1).uppercase()
    val backgroundColor = Color(0xFF00008B)

    Box(
        modifier = Modifier
            .size(40.dp)
            .background(backgroundColor, shape = CircleShape)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = firstLetter,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
    }
}

fun getChatId(userIdX: String, userIdY: String): String {
    val sortedIds = listOf(userIdX, userIdY).sorted()
    return "${sortedIds[0]}_${sortedIds[1]}"
}

@Composable
fun MessageItem(message: Message, userIdX: String?) {
    val isSentByUserX = message.senderId == userIdX
    val backgroundColor = if (isSentByUserX) Color(0xFF4169E1) else Color(0xFFF0F8FF)
    val textColor = if (isSentByUserX) Color.White else Color.Black

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = if (isSentByUserX) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .padding(horizontal = 8.dp)
                .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor,
                textAlign = if (isSentByUserX) TextAlign.End else TextAlign.Start
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = formatTimestamp(message.timestamp),
                style = MaterialTheme.typography.labelSmall,
                color = textColor.copy(alpha = 0.7f),
                textAlign = if (isSentByUserX) TextAlign.End else TextAlign.Start
            )
        }
    }
}

fun formatTimestamp(timestamp: Date?): String {
    if (timestamp == null) return ""
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(timestamp)
}
