package tg.crsandroid.carpool.presentation.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import tg.crsandroid.carpool.model.Message
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ChatScreen(userIdX: String?, userIdY: String?) {
    val db = FirebaseFirestore.getInstance()
    val messagesCollection = db.collection("c")
    var messages by remember { mutableStateOf<List<Message>>(emptyList()) }
    var newMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    // 🔹 État pour le défilement automatique
    val listState = rememberLazyListState()

    var userName by remember { mutableStateOf("") } // État pour stocker le nom de l'utilisateur

    // Récupérer le nom de l'utilisateur avec qui on discute
    LaunchedEffect(userIdY) {
        if (userIdY != null) {
            db.collection("users")
                .document(userIdY)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        userName = document.getString("nomUtilisateur") ?: "Inconnu"
                    }
                }
                .addOnFailureListener { e ->
                    println("Erreur lors de la récupération du nom de l'utilisateur : $e")
                }
        }
    }

    if (userIdX != null && userIdY != null) {
        val chatId = getChatId(userIdX, userIdY)

        // Récupérer les messages entre les deux utilisateurs
        DisposableEffect(chatId) {
            val query = db.collection("chats")
                .document(chatId)
                .collection("messages")
                .orderBy("timestamp") // Trier par timestamp

            val listener = query.addSnapshotListener {querySnapshot, error ->
                if (error != null) {
                    println("Erreur lors de la récupération des messages : $error")
                    return@addSnapshotListener
                }
                if (querySnapshot!= null) {
                    val messageList = querySnapshot.documents.mapNotNull { doc ->
                        val text = doc.getString("text") ?: ""
                        val senderId = doc.getString("senderId") ?: ""
                        val timestamp = doc.getTimestamp("timestamp")?.toDate()
                        val imageUrl = doc.getString("imageUrl") // Ajouter l'URL de l'image
                        Message(text, senderId, timestamp, imageUrl)
                    }
                    messages = messageList
                    isLoading = false
                }
            }

            // Nettoyage du listener quand le composable est détruit
            onDispose {
                listener.remove()
            }
        }
    }

    // 🔹 Faire défiler quand `messages` change
    LaunchedEffect(messages) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    // Fonction pour envoyer un message
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
                .set(mapOf(
                    "participants" to listOf(userIdX, userIdY),
                    "lastMessage" to newMessage,
                    "timestamp" to Timestamp.now()
                ), SetOptions.merge())

            db.collection("chats")
                .document(chatId)
                .collection("messages")
                .add(messageData)
                .addOnSuccessListener {
                    newMessage = "" // Réinitialiser le champ de texte
                }
                .addOnFailureListener { e ->
                    println("Erreur lors de l'envoi du message : $e")
                }

        }
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White // Arrière-plan bleu clair
    ){
        // Afficher les messages ou un message par défaut
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Bannière en haut avec le nom de l'utilisateur et une flèche pour revenir en arrière
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF4169E1))
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    println("Retour en arrière")
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Retour",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))

                // Icône de profil avec la première lettre du nom de l'utilisateur
                ProfileIcon(userName)

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (messages.isEmpty()) {
                Text(
                    text = "No messages yet",
                    style = MaterialTheme.typography.titleSmall,
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


            // Champ de texte et bouton d'envoi
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
                    placeholder = { Text("Entrez votre message...") }
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
                        tint = Color.White // Couleur de l'icône
                    )
                }
            }
        }
    }

}

@Composable
fun ProfileIcon(userName: String) {
    val firstLetter = userName.take(1).uppercase() // Prendre la première lettre du nom
    val backgroundColor = Color(0xFF00008B) // Couleur de fond de l'icône

    Box(
        modifier = Modifier
            .size(40.dp) // Taille de l'icône
            .background(backgroundColor, shape = CircleShape)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = firstLetter,
            style = MaterialTheme.typography.titleSmall,
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

// Fonction pour formater le timestamp en une chaîne lisible
fun formatTimestamp(timestamp: Date?): String {
    if (timestamp == null) return ""
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(timestamp)
}


