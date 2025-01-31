package tg.crsandroid.carpool.presentation.screens.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import tg.crsandroid.carpool.R


@Composable
fun SignInScreen(onNavigateToSignUp: () -> Unit={}) {
    // États pour les champs de texte
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // État pour afficher/masquer le mot de passe
    var isPasswordVisible by remember{ mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text="login",
            color=Color.Blue,
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold
        )
        // Image en haut
        Image(
            painter = painterResource(id = R.drawable.signup),
            contentDescription = "image login",
            modifier = Modifier
                .size(240.dp)
                .padding(bottom = 16.dp)
        )

        // Champ Nom d'utilisateur
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nom d'utilisateur") },
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

        // Bouton Se connecter
        Button(
            onClick = { /* Logique de connexion */ },
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
        LoginWithGoogle (onGoogleLoginClick = {

        })

        // Lien vers la page d'inscription
        TextButton(onClick = onNavigateToSignUp) {
            Text("Pas encore inscrit ?  S'inscrire", color = Color.Blue)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignInScreen() {
    SignInScreen(onNavigateToSignUp = {})
}