package tg.crsandroid.carpool.presentation.screens.Login

import SignUpScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
            SignInScreen(onNavigateToSignUp = {
                navController.navigate("signup")
            })
        }
        composable("signup") { // Route pour l'écran d'inscription
            SignUpScreen(onNavigateToSignIn = {
                navController.navigate("signin")
            })
        }
        composable("signin") {
            SignInScreen(onNavigateToSignUp = {
                navController.navigate("signup")
            })
        }
    }
}