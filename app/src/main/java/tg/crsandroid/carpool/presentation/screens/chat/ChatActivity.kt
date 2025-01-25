package tg.crsandroid.carpool.presentation.screens.chat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import tg.ulcrsandroid.jetchatcompose.ui.theme.JetChatComposeTheme

class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetChatComposeTheme {
                ChatScreen()
            }
        }
    }

    @Composable
    fun ChatScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF5F5F5), // Gris clair
                            Color.White // Blanc
                        )
                    )
                )
        ) {
            Scaffold(
                topBar = { ChatTopBar() },
                bottomBar = { ChatBottomBar() },
                content = { padding -> ChatContent(Modifier.padding(padding)) },
                containerColor = Color.Transparent // Fond transparent pour afficher l'arrière-plan
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ChatTopBar() {
        TopAppBar(
            title = {
                Text(
                    text = "Nom Utilisateur",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 10.dp)
                )
            },
            navigationIcon = {
                Row {
                    IconButton(onClick = { /* Action retour */ }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Icône retour",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Photo de profil",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(start = 8.dp),
                        tint = Color.Unspecified
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* Action de recherche */ }) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Icône de recherche",
                        modifier = Modifier.size(32.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White // Couleur pour la barre supérieure
            )
        )
    }

    @Composable
    fun ChatContent(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Messages ici", style = MaterialTheme.typography.bodyMedium)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ChatBottomBar() {
        var message by remember { mutableStateOf("") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(
                    color = Color.White,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { /* Action Emoji */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.emojis),
                    contentDescription = "Emoji",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Gray
                )
            }
            TextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                placeholder = { Text("Saisissez un message", color = Color.Gray) },
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFD7DFFC),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            IconButton(onClick = {
                println("Message envoyé : $message")
                message = "" // Efface le champ de texte après l'envoi
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.send),
                    contentDescription = "Icône d'envoi",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}