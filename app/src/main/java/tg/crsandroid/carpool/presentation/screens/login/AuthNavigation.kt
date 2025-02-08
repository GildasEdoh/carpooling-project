package tg.crsandroid.carpool.presentation.screens.Login

/*
import SignUpScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import tg.crsandroid.carpool.R
import tg.crsandroid.carpool.manager.FirebaseAuthManager


    @Composable
    fun AppNavigation(state: Int) {

        val navController = rememberNavController()
        var signStart = "signin"
        if (state == 1) {
            signStart = "signin"
        } else {
            signStart = "signup"
        }
        NavHost(
            navController = navController,
            startDestination = signStart// Écran de départ
        ) {
            composable("login") {
                LoginScreen(
                    onLoginClick = {
                        navController.navigate("signin")
                    },
                    onSignUpClick = {
                        navController.navigate("signup")
                    },
                    onGoogleLoginClick = {}
                )
            }
            composable("signup") { // Route pour l'écran d'inscription
                SignUpScreen(onNavigateToSignIn = {
                    navController.navigate("signin")
                })
            }
            composable("signin") {
                SignInScreen(
                    onNavigateToSignUp = {
                        navController.navigate("signup")
                    },
                    onLoginSuccess = {
                        Log.i("AHTH", "AUthentification")
                    }
                )
            }
        }
    }
*/