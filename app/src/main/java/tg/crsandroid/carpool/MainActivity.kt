package tg.crsandroid.carpool

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import tg.crsandroid.carpool.manager.FirebaseAuthManager
import tg.crsandroid.carpool.presentation.screens.Login.LoginScreen
import tg.crsandroid.carpool.presentation.screens.ride.RideListActivity


class MainActivity : ComponentActivity() {

    val db = Firebase.firestore
    val TAG = "BASE Firestore"
    private var authManager = FirebaseAuthManager()
    private lateinit var googleSignClient : GoogleSignInClient
    // private val googleSignInClient = remenber {provideGoo}
    val user = hashMapOf(
        "first" to "ada",
        "last" to "lovelace",
        "born" to 2015,
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            /*
            LoginScreen(
                onLoginClick = {
                    print("cliecj")
                   //  startRideList()
                },
                onSignUpClick =  { print("cliecj") },
                onGoogleLoginClick = {  },
            )*/
            AppContent()
        }
        // Create a new user with a first, middle, and last name
        val user2 = hashMapOf(
            "first" to "Alan",
            "middle" to "Mathison",
            "last" to "Turing",
            "born" to 1912,
        )
    }

    @Composable
    fun AppContent() {
        val context = LocalContext.current

        // Fournit une instance de GoogleSignInClient
        val googleSignInClient = remember {
            provideGoogleSignInClient(context)
        }

        // Gestionnaire pour lancer l'intent de connexion Google
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            Log.i("Page auth", "code : ${result.resultCode}, data : ${result.data}")
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(context, task)
            } else {
                Toast.makeText(context, "Connexion annulée", Toast.LENGTH_SHORT).show()
            }
        }

        LoginScreen(
            onLoginClick = {
                // Logique pour le bouton Login
                println("Login clicked")
            },
            onSignUpClick = {
                // Logique pour le bouton Sign Up
                println("Sign Up clicked")
            },
            onGoogleLoginClick = {
                // Lancer l'intent Google Sign-In
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            }
        )
    }
    private fun provideGoogleSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    private fun handleSignInResult(context: Context, task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            account?.let {
                authManager.signInWithGoogle(it) { isSuccess, error ->
                    if (isSuccess) {
                        Toast.makeText(
                            context,
                            "Connexion réussie : ${it.displayName}",
                            Toast.LENGTH_SHORT
                        ).show()

                        startRideList()
                    } else {
                        Toast.makeText(context, "Erreur : $error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: ApiException) {
            Toast.makeText(context, "Google sign-in échoué : ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    fun startRideList() {
        val intent = Intent(this, RideListActivity::class.java)
        startActivity(intent)
    }
}
