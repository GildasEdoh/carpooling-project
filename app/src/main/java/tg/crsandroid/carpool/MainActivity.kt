package tg.crsandroid.carpool

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import tg.crsandroid.carpool.presentation.screens.Login.LoginScreen
import tg.crsandroid.carpool.presentation.screens.home.HomeScreen
import tg.crsandroid.carpool.ui.theme.CarpoolTheme

class MainActivity : ComponentActivity() {
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
    }
}
