package tg.crsandroid.carpool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import tg.crsandroid.carpool.presentation.screens.Login.LoginScreen
import tg.crsandroid.carpool.presentation.screens.home.HomeScreen
import tg.crsandroid.carpool.ui.theme.CarpoolTheme

class MainActivity : ComponentActivity() {
    val db = Firebase.firestore
    val TAG = "BASE Firestore"
    val user = hashMapOf(
        "first" to "ada",
        "last" to "lovelace",
        "born" to 2015,
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginScreen(
                onLoginClick = { print("cliecj") },
                onSignUpClick =  { print("cliecj") },
                onGoogleLoginClick = { print("cliecj") },
            )
//            HomeScreen()
        }
        // Create a new user with a first, middle, and last name
        val user2 = hashMapOf(
            "first" to "Alan",
            "middle" to "Mathison",
            "last" to "Turing",
            "born" to 1912,
        )
    }
}
