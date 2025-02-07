
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.carpooling_project.model.Utilisateur
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tg.crsandroid.carpool.R
import tg.crsandroid.carpool.manager.FirebaseAuthManager
import tg.crsandroid.carpool.service.FirestoreService
import tg.crsandroid.carpool.service.FirestoreService.scope

@Composable
fun SignUpScreen(onNavigateToSignIn: () -> Unit, viewModel: FirebaseAuthManager = FirebaseAuthManager()) {
    // États pour les champs de texte
    var username by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // État pour afficher/masquer le mot de passe
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign up",
            color = Color.Blue,
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold
        )

        // Image en haut
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "image signup",
            modifier = Modifier
                .size(240.dp)
                .padding(bottom = 16.dp)
        )

        // Champ Prénom
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("Prénom") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = "Prénom")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Champ Nom
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Nom") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = "Nom")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Champ E-mail
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Adresse e-mail") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = {
                Icon(Icons.Filled.Email, contentDescription = "E-mail")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Champ Mot de passe avec icônes
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(Icons.Filled.Lock, contentDescription = "Mot de passe")
            },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Masquer le mot de passe" else "Afficher le mot de passe"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton S'inscrire
        Button(
            onClick = {
                val userData = Utilisateur(
                    id = "",
                    nom = firstName,
                    prenom = lastName,
                    motDePasse = password
                )
                FirestoreService.currentUser = userData
                save(userData) { isSuccess ->
                    if (isSuccess) {
                        Log.i("Main", "Utilisateur ajouté")
                    } else {
                        Log.i("Main", "Utilisateur  non ajouté")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            )
        ) {
            Text("S'inscrire")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lien vers la page de connexion
        TextButton(onClick = onNavigateToSignIn) {
            Text("Déjà inscrit ? Se connecter", color = Color.Blue)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(onNavigateToSignIn = {})
}
private fun save(user:Utilisateur, callback: (Boolean) -> Unit) {
    launchSuspendFunction(scope, callback) {
        FirestoreService.usersRepo.addUser(user)
    }
}
fun launchSuspendFunction(scope: CoroutineScope, callback: (Boolean) -> Unit, suspendFunction: suspend () -> Boolean) {
    scope.launch {
        val result = suspendFunction()
        callback(result)
    }
}

