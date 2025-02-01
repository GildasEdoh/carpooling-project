package tg.crsandroid.carpool.presentation.screens.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import tg.crsandroid.carpool.model.Chat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChatHomeScreen(userIdX: String, navController: NavController) {
    val viewModel: ChatHomeViewModel = viewModel()
    val chats by viewModel.chats.collectAsState()

    LaunchedEffect(userIdX) {
        viewModel.loadChats(userIdX)
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        if (chats.isEmpty()) {
            Text(
                text = "Aucun chat disponible",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn {
                items(chats) { chat ->
                    ChatItem(chat = chat, userIdX = userIdX, onClick = {
                        // Naviguer vers le ChatScreen correspondant
                        navController.navigate("chatScreen/${chat.user1}/${chat.user2}")
                    })
                }
            }
        }
    }
}

@Composable
fun ChatItem(chat: Chat, userIdX: String, onClick: () -> Unit) {
    val otherUserName = if (chat.user1 == userIdX) chat.user2 else chat.user1

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = otherUserName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = chat.lastMessage,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = formatTimestamp(chat.timestamp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

// Fonction pour formater le timestamp en une chaîne lisible
fun formatTimestamp(timestamp: Date): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(timestamp)
}