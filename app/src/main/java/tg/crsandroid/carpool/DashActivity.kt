package tg.crsandroid.carpool

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import tg.crsandroid.carpool.presentation.screens.chat.ChatHomeScreen
import tg.crsandroid.carpool.presentation.screens.chat.ChatScreen
import tg.crsandroid.carpool.presentation.screens.home.Content
import tg.crsandroid.carpool.presentation.screens.login.ProfileInterface
import tg.crsandroid.carpool.presentation.screens.ride.RideListScreen
import tg.crsandroid.carpool.service.FirestoreService

class DashActivity : ComponentActivity() {
    // Firebase and Authentication properties
    private val db = Firebase.firestore
    val idx: String = FirestoreService.currentUser.id!!
    lateinit var idy: String;
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
            composable("ChatScreen/{user1}/{user2}",
                arguments = listOf(
                    navArgument("user1") { type = NavType.StringType },
                    navArgument("user2") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val user1 = backStackEntry.arguments?.getString("user1") ?: ""
                val user2 = backStackEntry.arguments?.getString("user2") ?: ""
                ChatScreen(navController, user1, user2)
                Log.i("Dash", "Not initialized")
            }
            composable("ChatHome") {
                ChatHomeScreen(idx, navController)
                Log.i("Dash", "Not initilized")
            }
            composable("Profil") {
                // ProfileScreen(navController)
                // Log.i("Dash", "Not initilized")
                ProfileInterface(navController,
                    onBackPressed = {
                        navController.popBackStack()
                    })
            }
            composable("listeTrajets") {
                RideListScreen(onBackPressed = {
                    navController.popBackStack()
                }, navController
                )
                Log.i("Dash", "Not initilized")
            }
        }
    }
}

