package tg.crsandroid.carpool.presentation.screens.Login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import tg.crsandroid.carpool.R
import tg.crsandroid.carpool.manager.FirebaseAuthManager
import tg.crsandroid.carpool.service.ConnexionService
import tg.crsandroid.carpool.ui.theme.poppinsFontFamily

@Composable
fun LoginScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                .width(400.dp) // Largeur de la carte
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
                GoogleSignInButton(onSignInSuccess = {
                    navController.navigate("Home")
                })

                // Row pour Login et Sign Up sur la même ligne
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly // Espace uniforme entre les boutons
                ) {
                    // Bouton Login
                    Button(
                        onClick = {
                            navController.navigate("SignIn")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF001F7F)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f) // Chaque bouton prend la même largeur
                            .padding(end = 8.dp) // Espacement entre les boutons
                            .height(50.dp) // Hauteur du bouton
                    ) {
                        Text(
                            text = "Login",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                        )
                    }

                    // Bouton Sign Up
                    Button(
                        onClick = {
                            navController.navigate("SignUp")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF001F7F)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f) // Même largeur que le bouton Login
                            .padding(start = 8.dp) // Espacement entre les boutons
                            .height(50.dp) // Hauteur du bouton
                    ) {
                        Text(text = "Sign Up",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun GoogleSignInButton(onSignInSuccess: () -> Unit) {
    val context = LocalContext.current
    val auth = Firebase.auth

    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSignInSuccess()
                    } else {
                        // Handle failure
                    }
                }
        } catch (e: ApiException) {
            // Handle failure
        }
    }

    Button(onClick = {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        signInLauncher.launch(googleSignInClient.signInIntent)
    }) {
        Text("Login with Google")
    }
}

@Composable
fun LoginPageImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp) // Hauteur fixe de l'image
    ) {
        // Image de fond
        Image(
            painter = painterResource(id = R.drawable.home_img),
            contentDescription = "Login Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit // Respect des proportions de l'image
        )

        // Contenu aligné en bas de l'image
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp), // Espacement en bas
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Texte principal
            Text(
                text = "Carpool",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 52.sp
            )

            // Paragraphe d'accroche
            Text(
                text = "Simplifiez vos trajets quotidiens avec des solutions de covoiturage adaptées à vos besoins.",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color.Black.copy(alpha = 0.8f), // Transparence pour un contraste doux
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp) // Marges sur les côtés
            )
        }
    }
}
