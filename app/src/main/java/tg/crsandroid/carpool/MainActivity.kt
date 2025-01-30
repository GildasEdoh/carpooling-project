package tg.crsandroid.carpool

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import tg.crsandroid.carpool.manager.FirebaseAuthManager
import tg.crsandroid.carpool.presentation.screens.Login.LoginScreen
import tg.crsandroid.carpool.presentation.screens.Map.MapScreen
import tg.crsandroid.carpool.presentation.screens.home.HomeScreen
import tg.crsandroid.carpool.presentation.screens.home.HomeScreenPreview
import tg.crsandroid.carpool.presentation.screens.ride.RideListActivity
import kotlin.properties.Delegates

class MainActivity : ComponentActivity() {
    // Firebase and Authentication properties
    private val db = Firebase.firestore
    private val authManager = FirebaseAuthManager()
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Google Sign In
//        initializeGoogleSignIn()

        setContent {
//            AppContent()
            // Initialiser FusedLocationProviderClient
            // Démarrer les mises à jour de localisation

            HomeScreen()

//            startLocationUpdates()
//            MapScreen(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .fillMaxWidth(),
////                longitude = longitude,
////                latitude = latitude,
//            )
        }
    }

    //initialisation de l'authentification par google
    private fun initializeGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    @Composable
    fun AppContent() {
        val context = LocalContext.current

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(context, task)
            } else {
                showToast(context, "Connexion annulée")
            }
        }

        LoginScreen(
            onLoginClick = {
                // Handle email/password login here if needed
                showToast(context, "Login clicked")
            },
            onSignUpClick = {
                // Handle sign up here if needed
                showToast(context, "Sign Up clicked")
            },
            onGoogleLoginClick = {
                startGoogleSignIn(launcher)
            }
        )
    }

    private fun startGoogleSignIn(launcher: ActivityResultLauncher<Intent>) {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private fun handleSignInResult(context: Context, task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            account?.let {
                authManager.signInWithGoogle(it) { isSuccess, error ->
                    if (isSuccess) {
                        // Save user data if needed
                        saveUserToFirestore(it)
                        // Navigate to RideList screen
                        navigateToRideList()
                        showToast(context, "Connexion réussie : ${it.displayName}")
                    } else {
                        showToast(context, "Erreur : $error")
                    }
                }
            }
        } catch (e: ApiException) {
            showToast(context, "Google sign-in échoué : ${e.message}")
        }
    }

    private fun saveUserToFirestore(account: GoogleSignInAccount) {
        val userData = hashMapOf(
            "email" to account.email,
            "displayName" to account.displayName,
            "photoUrl" to (account.photoUrl?.toString() ?: ""),
            "lastLogin" to System.currentTimeMillis()
        )

        account.id?.let { userId ->
            db.collection("users")
                .document(userId)
                .set(userData)
                .addOnFailureListener { e ->
                    println("Error saving user data: ${e.message}")
                }
        }
    }

    private fun navigateToRideList() {
        Intent(this, RideListActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(this)
            finish() //
        }
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

