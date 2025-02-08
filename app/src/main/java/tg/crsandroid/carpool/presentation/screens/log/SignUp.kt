
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.carpooling_project.model.Utilisateur
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tg.crsandroid.carpool.R
import tg.crsandroid.carpool.manager.FirebaseAuthManager
import tg.crsandroid.carpool.service.FirestoreService
import tg.crsandroid.carpool.service.FirestoreService.scope

@Composable
fun SignUpScreen(navController: NavController) {
    // États pour les champs de texte
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // État pour afficher/masquer le mot de passe
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    //Message d'erreur
    var errorMessage by remember { mutableStateOf("") }

    // Contexte pour afficher des Toast
    val context = LocalContext.current

    //Firebase/Firestore
    val auth = Firebase.auth
    val db = Firebase.firestore

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

        Spacer(modifier = Modifier.height(8.dp))

        // Champ Confirmation du Mot de passe avec icônes
        OutlinedTextField(value = confirmPassword, onValueChange = { confirmPassword = it },
            label = { Text("Confirmer le mot de passe") }, modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Confirmer le mot de passe") },
            trailingIcon = {
                IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                    Icon(
                        imageVector = if (isConfirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (isConfirmPasswordVisible) "Masquer le mot de passe" else "Afficher le mot de passe"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Affichage des erreurs
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp
            )
        }

        // Bouton S'inscrire
        Button(
            onClick = {
                if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                    errorMessage = "Veuillez remplir tous les champs."
                    Log.i("SignUpScreen", "Veuillez remplir tous les champs.")
                    return@Button
                }
                if (password != confirmPassword) {
                    errorMessage = "Les mots de passe ne correspondent pas."
                    Log.i("SignUpScreen", "Les mots de passe ne correspondent pas.")
                    return@Button
                }

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid ?: ""

                            val userData = Utilisateur(
                                id = userId,
                                nom = lastName,
                                prenom = firstName,
                                motDePasse = password
                            )

                            db.collection("users").document(userId)
                                .set(userData)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Utilisateur enregistré !", Toast.LENGTH_SHORT).show()
                                    Log.i("SignUpScreen", "Utilisateur enregistré avec succès")
                                    navController.navigate("SignIn")
                                }
                                .addOnFailureListener {
                                    errorMessage = "Echec lors de l'enregistrement"
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                                    Log.e("SignUpScreen", "Erreur lors de l'enregistrement dans Firestore")
                                }
                        } else {
                            errorMessage = "Echec lors de l'enregistrement"
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            Log.e("SignUpScreen", "Erreur lors de l'enregistrement", task.exception)
                        }
                    }

                /*val userData = Utilisateur(
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
                }*/
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
        TextButton(onClick = { navController.navigate("SignIn") }) {
            Text("Déjà inscrit ? Se connecter", color = Color.Blue)
        }
    }
}

/*@Preview(showBackground = true)
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
}*/

