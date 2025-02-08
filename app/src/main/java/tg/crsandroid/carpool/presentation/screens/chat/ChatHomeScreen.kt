package tg.crsandroid.carpool.presentation.screens.chat

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.carpooling_project.model.Utilisateur
import tg.crsandroid.carpool.model.Chat
import tg.crsandroid.carpool.service.FirestoreService
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHomeScreen(userIdX: String, navController: NavController) {
    val viewModel: ChatHomeViewModel = viewModel()
    val chats by viewModel.chats.collectAsState()

    LaunchedEffect(userIdX) {
        viewModel.loadChats(userIdX)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Discussions") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF3C52C5),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            if (chats.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Aucun chat",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Aucune discussion disponible",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.padding(horizontal = 8.dp)) {
                    items(chats) { chat ->
                        ChatItem(chat = chat, userIdX = userIdX, onClick = {
                            navController.navigate("ChatScreen/${chat.user1}/${chat.user2}")
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun ChatItem(chat: Chat, userIdX: String, onClick: () -> Unit) {
    var otherUserName = if (chat.user1 == userIdX) chat.user2 else chat.user1
    var otherUser by remember { mutableStateOf<Utilisateur?>(null) } // Utilisation de mutableStateOf

    LaunchedEffect(Unit) {
        try {
            val fetchedUser = getUserById(otherUserName)
            if (fetchedUser != null) {
                otherUser = fetchedUser // Déclenche la recomposition
                Log.i("SUCCESS", "Data fetched successfully : ${fetchedUser.nom}")
            }
        } catch (e: Exception) {
            Log.e("ERROR", "Error fetching data", e)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Utilisateur",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 12.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = otherUser?.nom ?: "Chargement...",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = chat.lastMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = formatTimestamp(chat.timestamp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

private suspend fun getUserById(id: String) : Utilisateur? {
    return try {
        FirestoreService.usersRepo.getUserById(id)
    } catch (e: Exception) {
        null
    }
}