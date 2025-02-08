package tg.crsandroid.carpool.presentation.screens.log

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import tg.crsandroid.carpool.R
import tg.crsandroid.carpool.presentation.screens.navigation.BottomNavigationBar
import tg.crsandroid.carpool.service.FirestoreService

@Composable
fun ProfileInterface(navController: NavController, onBackPressed: () -> Unit) {
    val currUser = FirestoreService.currentUser

    var nom by remember { mutableStateOf(currUser.nom ?: "") }
    var prenom by remember { mutableStateOf(currUser.prenom ?: "") }
    var email by remember { mutableStateOf(currUser.email ?: "") }
    var nomUtilisateur by remember { mutableStateOf(currUser.nomUtilisateur ?: "${currUser.nom}${currUser.prenom}") }
    var type by remember { mutableStateOf(if (currUser.type.isNullOrEmpty()) "Passager" else currUser.type!!) }
    var id by remember { mutableStateOf(currUser.id ?: "") }

    var isEditing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFFFFFFF)),


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

        Spacer(modifier = Modifier.height(8.dp))

        // Photo de profil
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Profile Photo",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Card pour afficher les informations utilisateur
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF) // Couleur de fond personnalisée (gris clair)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
                    ,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                if (!isEditing) {
                    Text(text = "ID: $id", fontSize = 14.sp, fontWeight = FontWeight.Light, color = Color.Gray)
                    Text(text = "$nom $prenom", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Nom d'utilisateur: $nomUtilisateur", fontSize = 18.sp, color = MaterialTheme.colorScheme.secondary)
                    Text(text = "Statut: $type", fontSize = 18.sp, color = MaterialTheme.colorScheme.secondary)
                    Text(text = "Email: $email", fontSize = 18.sp, color = MaterialTheme.colorScheme.secondary)
                } else {
                    OutlinedTextField(value = nom, onValueChange = { nom = it }, label = { Text("Nom") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = prenom, onValueChange = { prenom = it }, label = { Text("Prénom") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = nomUtilisateur, onValueChange = { nomUtilisateur = it }, label = { Text("Nom d'utilisateur") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Statut (Chauffeur ou Passager)") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Boutons d'édition et de sauvegarde
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { isEditing = !isEditing },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4169E1))
            ) {
                Icon(imageVector = if (isEditing) Icons.Default.Save else Icons.Default.Edit, contentDescription = "Edit Icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = if (isEditing) "Save" else "Edit Profile")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bouton Déconnexion bien séparé
        Button(
            onClick = { /* Action pour Déconnexion */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4169E1)),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .width(200.dp)
        ) {
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "icone de log out")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Log Out")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // BottomNavigationBar repositionné
        BottomNavigationBar(navController)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProfileInterface() {
    // ProfileInterface(navController = rememberNavController(), onBackPressed = {})
}
