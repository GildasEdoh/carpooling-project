package tg.crsandroid.carpool.presentation.screens.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tg.crsandroid.carpool.ui.theme.poppinsFontFamily

@Composable
fun LoginPage(
    onLogin: (email: String, password: String) -> Unit,
    onNavigateToSignUp: () -> Unit,
    onGoogleSignIn: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Login", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = { onLogin(email, password) }, modifier = Modifier.padding(top = 8.dp)) {
            Text("Login")
        }
        TextButton(onClick = onNavigateToSignUp) {
            Text("Don't have an account? Sign Up")
        }
        Button(onClick = onGoogleSignIn, modifier = Modifier.padding(top = 8.dp)) {
            Text("Sign in with Google")
        }
    }
}
/*
=======
@Composable
fun LoginScreen(onLoginClick: () -> Unit, onSignUpClick: () -> Unit, onGoogleLoginClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.horizontalGradient(colors = listOf(
                Color(0xFFD32F2F),
                Color(0xFFE03333),
                Color(0xFFD9001D),
            )))
    ) {
        // Conteneur pour centrer l'image
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 200.dp), // Ajuste l'espacement pour que l'image ne chevauche pas la carte
            contentAlignment = Alignment.Center // Centre l'image horizontalement et verticalement
        ) {
            LoginPageImage() // Appel de l'image
        }

        // Conteneur de boutons, aligné en bas
        Card(
            modifier = Modifier
//                .fillMaxWidth()
                .width(415.dp) // Largeur de la carte
//                .height(150.dp) // Hauteur de la carte
                .padding(bottom = 16.dp) // Espacement en bas
                .align(Alignment.BottomCenter), // Aligner la carte en bas au centre
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp) // Moved here
            ) {
                // Bouton de connexion avec Google
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
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }

                // Row pour Login et Sign Up sur la même ligne
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly // Espace uniforme entre les boutons
                ) {
                    // Bouton Login
                    Button(
                        onClick = onLoginClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF001F7F)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f) // Chaque bouton prend la même largeur
                            .padding(end = 8.dp) // Espacement entre les boutons
                            .height(50.dp) // Hauteur du bouton
                    ) {
                        Text(text = "Login", color = Color.White, fontSize = 16.sp)
                    }

                    // Bouton Sign Up
                    Button(
                        onClick = onSignUpClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF001F7F)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f) // Même largeur que le bouton Login
                            .padding(start = 8.dp) // Espacement entre les boutons
                            .height(50.dp) // Hauteur du bouton
                    ) {
                        Text(text = "Sign Up", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}*/

@Composable
fun LoginPageImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Hauteur de l'image
    ) {
        Text(
            text = "Override",
            modifier = Modifier
                .align(Alignment.BottomCenter),
            fontFamily = poppinsFontFamily,
            color = Color.White,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
