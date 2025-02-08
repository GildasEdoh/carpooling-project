package tg.crsandroid.carpool.presentation.screens.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tg.crsandroid.carpool.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileInterface(onBackPressed = { finish() })
        }
    }
}

@Composable
fun ProfileInterface(onBackPressed: () -> Unit) {
    var username by remember { mutableStateOf("John Doe") }
    var status by remember { mutableStateOf("Statue") }
    var isEditing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icône de retour en arrière
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackPressed) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }

        // Photo de profil
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Profile Photo",
            modifier = Modifier
                .size(230.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Affichage du nom et du statut
        if (!isEditing) {
            Text(text = username, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text(text = status, fontSize = 22.sp, color = MaterialTheme.colorScheme.secondary)
        } else {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nom d'utilisateur") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = status,
                onValueChange = { status = it },
                label = { Text("Statut (Chauffeur ou Passager)") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Bouton Editer / Sauvegarder (en bleu)
        Button(
            onClick = { isEditing = !isEditing },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4169E1)), // Couleur bleue
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Icon")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = if (isEditing) "Save" else "Edit Profile")
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bouton Déconnexion
        Button(
            onClick = { /* Action pour Déconnexion */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4169E1)),
            modifier = Modifier
                .padding(top = 16.dp)
                .width(200.dp)
        ) {
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "icone de log out")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Log Out")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProfileInterface() {
    ProfileInterface(onBackPressed = {})
}
