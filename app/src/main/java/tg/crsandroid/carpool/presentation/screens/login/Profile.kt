
package tg.crsandroid.carpool.presentation.screens.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carpooling_project.model.Utilisateur
import tg.crsandroid.carpool.R

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF3C52C5),
                    secondary = Color(0xFF3C52C5),
                    surface = Color(0xFFFFFFFF)
                )
            ) {
                ProfileScreen(
                    user = Utilisateur(
                        id = "1",
                        nom = "John Doe",
                        email = "john.doe@example.com",
                        avatar = R.drawable.profile // Replace with your avatar resource
                    ),
                    onLogout = { finish() } // Close the activity on logout
                )
            }
        }
    }
}

@Composable
fun ProfileScreen(
    user: Utilisateur,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ProfileHeader(user)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onLogout,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ),
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            Text("Déconnexion", fontSize = 18.sp, fontFamily = FontFamily(Font(R.font.poppins_semibold)))
        }
    }
}

@Composable
fun ProfileHeader(user: Utilisateur, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val avatar: Painter = painterResource(id = user.avatar)
        Image(
            painter = avatar,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
                .background(Color.Gray, CircleShape)
                .padding(3.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = user.nom,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.poppins_bold)),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = user.email,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
    }
}