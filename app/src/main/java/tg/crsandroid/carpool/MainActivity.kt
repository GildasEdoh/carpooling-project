package tg.crsandroid.carpool

import SignUpScreen
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.carpooling_project.model.Utilisateur
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tg.crsandroid.carpool.manager.FirebaseAuthManager
import tg.crsandroid.carpool.presentation.screens.Login.LoginScreen
import tg.crsandroid.carpool.presentation.screens.Login.SignInScreen
import tg.crsandroid.carpool.presentation.screens.chat.ChatHomeScreen
import tg.crsandroid.carpool.presentation.screens.chat.ChatScreen
import tg.crsandroid.carpool.presentation.screens.home.Content
import tg.crsandroid.carpool.presentation.screens.home.HomePage
import tg.crsandroid.carpool.presentation.screens.log.ProfileInterface
import tg.crsandroid.carpool.presentation.screens.ride.RideListScreen
import tg.crsandroid.carpool.service.FirestoreService
import tg.crsandroid.carpool.service.FirestoreService.scope
import tg.crsandroid.carpool.service.FirestoreService.trajets
import tg.crsandroid.carpool.service.userDetails

class MainActivity : ComponentActivity() {
    // Firebase and Authentication properties
    val db = Firebase.firestore
    val authManager = FirebaseAuthManager()
    lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Google Sign In
        initializeGoogleSignIn()

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "Login") {
                composable("Login") {
                    AppContent(navController)
                }
                composable("SignIn") {
                    SignInScreen(navController)
                }
                composable("SignUp") {
                    SignUpScreen(navController)
                }
            }
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
    fun AppContent(navController: NavController) {
        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            Log.i("MAIN", "result code ${result.resultCode}" +"RESULT_OK" + RESULT_OK)
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(context, task)
            } else {
                showToast(context, "Connexion annulée")
            }
        }

        LoginScreen(navController)

        /*LoginScreen(
            onLoginClick = {
                // Handle email/password log here if needed
                showToast(context, "Login clicked")
            },
            onSignUpClick = {
                // Handle sign up here if needed
                showToast(context, "Sign Up clicked")
            },
            onGoogleLoginClick = {
                startGoogleSignIn(launcher)
            }
        )*/
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
                        // navigateToRideList()
                        startDashBoard()
                        showToast(context, "Connexion réussie : ${it.displayName}")
                    } else {
                        // startDashBoard()
                        showToast(context, "Erreur : $error")
                    }
                }
            }
        } catch (e: ApiException) {
            showToast(context, "Google sign-in échoué : ${e.message}")
        }
    }

    private fun saveUserToFirestore(account: GoogleSignInAccount) {
        val userData = Utilisateur(
            account.id,
            account.givenName,
            account.familyName,
            account.email
        )
        FirestoreService.currentUser = userData
        save(userData) { isSuccess ->
            if (isSuccess) {
                Log.i("Main", "Utilisateur ajouté")
            } else {
                Log.i("Main", "Utilisateur  non ajouté")
            }
        }

    }


    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    private fun startDashBoard() {
        Intent(this, DashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(this)
            finish() //
        }
    }
    private fun save(user:Utilisateur, callback: (Boolean) -> Unit) {
        launchSuspendFunction(scope, callback) {
            FirestoreService.usersRepo.addUser(user)
        }
    }

    fun launchSuspendFunction(scope: CoroutineScope, callback: (Boolean) -> Unit, suspendFunction: suspend () -> Boolean) {
        scope.launch {
            val result = suspendFunction()
            callback(result)
        }
    }
}

