package tg.crsandroid.carpool

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import tg.crsandroid.carpool.manager.FirebaseAuthManager
import tg.crsandroid.carpool.presentation.screens.Login.LoginScreen
import tg.crsandroid.carpool.presentation.screens.chat.ChatScreen
import tg.crsandroid.carpool.presentation.screens.home.Content

class DashActivity : ComponentActivity() {
    // Firebase and Authentication properties
    private val db = Firebase.firestore
   //  private val authManager = FirebaseAuthManager()
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            // Demarrage du dashbord de l'utilisateur
            // Debute avec l' acceuil
            MainApp()
        }
    }
    @Composable
    fun MainApp() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "Home") {
            composable("Home") {
                Content(navController)
            }
            composable("History") {
                // HistoryScreen(navController)
                Log.i("Dash", "Not initilized")
            }
            composable("Chat") {
                 ChatScreen(navController)
                Log.i("Dash", "Not initilized")
            }
            composable("Profil") {
                // ProfileScreen(navController)
                Log.i("Dash", "Not initilized")
            }
            composable("listeTrajets") {
                Log.i("Dash", "Not initilized")
            }
        }
    }
}

