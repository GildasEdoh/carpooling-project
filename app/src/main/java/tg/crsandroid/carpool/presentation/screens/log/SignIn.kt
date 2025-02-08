package tg.crsandroid.carpool.presentation.screens.Login

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tg.crsandroid.carpool.DashActivity
import tg.crsandroid.carpool.R
import tg.crsandroid.carpool.manager.FirebaseAuthManager
import tg.crsandroid.carpool.service.ConnexionService
import tg.crsandroid.carpool.service.FirestoreService
import tg.crsandroid.carpool.service.FirestoreService.scope
import tg.crsandroid.carpool.ui.theme.poppinsFontFamily


@Composable
fun SignInScreen(navController: NavController) {
    // États pour les champs de texte
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // État pour afficher/masquer le mot de passe
    var isPasswordVisible by remember{ mutableStateOf(false)}

    //Message d'erreur
    var errorMessage by remember { mutableStateOf("") }

    // Contexte pour afficher des Toast
    val context = LocalContext.current

    // Firebase Authentication
    val auth = Firebase.auth

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text="log",
            color=Color.Blue,
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold
        )
        // Image en haut
        Image(
            painter = painterResource(id = R.drawable.signup),
            contentDescription = "image log",
            modifier = Modifier
                .size(240.dp)
                .padding(bottom = 16.dp)
        )

        // Champ Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            leadingIcon ={
                Icon( Icons.Default.Person,contentDescription ="utilisateur")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Champ Mot de passe avec icônes
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon( Icons.Filled.Lock,
                    contentDescription = "Mot de passe"
                )
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

        // Lien "Mot de passe oublié ?"
        TextButton(onClick = { /* Logique pour mot de passe oublié */ }) {
            Text("Mot de passe oublié ?", color = Color.Blue)
        }
        Spacer(modifier = Modifier.height(16.dp))
        
        // Affichage des erreurs
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp
            )
        }

        // Bouton Se connecter
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Veuillez remplir tous les champs."
                    return@Button
                }

                // Connexion avec Firebase
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            // Connexion réussie
                            Toast.makeText(context, "Connexion réussie !", Toast.LENGTH_SHORT).show()
                            navController.navigate("Home") // Naviguer vers l'écran principal
                        } else {
                            // Échec de la connexion
                            errorMessage = task.exception?.message ?: "Erreur de connexion"
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }

            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            )
        ) {
            Text("Se connecter")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Séparateur "_or_"
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier.weight(1f),
                color = Color.Gray,
                thickness = 1.dp
            )
            Text(
                text = "or",
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Gray,
                fontSize = 14.sp
            )
            Divider(
                modifier = Modifier.weight(1f),
                color = Color.Gray,
                thickness = 1.dp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Bouton Google
        GoogleSignInButton(onSignInSuccess = {
            startDashBoard(context)
        })

        // Lien vers la page d'inscription
        TextButton(onClick = {
            navController.navigate("SignUp")
        }) {
            Text("Pas encore inscrit ?  S'inscrire", color = Color.Blue)
        }
    }
}
private fun startDashBoard(context: Context) {
    val intent = Intent(context, DashActivity::class.java)
    context.startActivity(intent)
}
@Composable
fun LoginWithGoogle(onGoogleLoginClick: () -> Unit) {
    Button(
        onClick = onGoogleLoginClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEFEFEF)),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp) // Hauteur du bouton
    ) {
        Text(
            text = "Login with Google",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            fontFamily = poppinsFontFamily,
            color = Color.Gray
        )
    }
}