package tg.crsandroid.carpool.presentation.screens.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tg.crsandroid.carpool.R
import tg.crsandroid.carpool.ui.theme.poppinsFontFamily

@Composable
fun LoginScreen(onLoginClick: () -> Unit, onSignUpClick: () -> Unit, onGoogleLoginClick: () -> Unit) {
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
                        onClick = onSignUpClick,
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

//@Composable
//fun LoginPageImage() {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//            .height(400.dp) // Hauteur de l'image
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.home_img),
//            contentDescription = "Login Image",
//            modifier = Modifier
//                .fillMaxSize()
//        )
//        Text(
//            text = "Carpool",
//            modifier = Modifier.align(Alignment.BottomCenter),
//            fontFamily = poppinsFontFamily,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black,
//            fontSize = 52.sp
//        )
//    }
//}

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
