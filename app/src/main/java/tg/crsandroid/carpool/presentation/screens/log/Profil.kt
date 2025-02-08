package tg.crsandroid.carpool.presentation.screens.log


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }

    LaunchedEffect(currentUser) {
        currentUser?.let {
            db.collection("users")
                .document(it.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        firstName = document.getString("firstName") ?: ""
                        lastName = document.getString("lastName") ?: ""
                    }
                }
                .addOnFailureListener { e ->
                    println("Erreur lors de la récupération des informations de l'utilisateur : $e")
                }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProfileIcon(name = "$firstName $lastName")
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "$firstName $lastName",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = email,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    //auth.signOut()
                    /*navController.navigate("log") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }*/
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4169E1))
            ) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Se déconnecter",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Se déconnecter",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ProfileIcon(name: String) {
    val initials = name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString("")

    Box(
        modifier = Modifier
            .size(80.dp)
            .background(Color(0xFF4169E1), shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
