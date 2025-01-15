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
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
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
            CarpoolTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        db.collection("users-test").add(user)
            .addOnSuccessListener{ documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")


            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
        // Create a new user with a first, middle, and last name
        val user2 = hashMapOf(
            "first" to "Alan",
            "middle" to "Mathison",
            "last" to "Turing",
            "born" to 1912,
        )

        // Add a new document with a generated ID
        db.collection("users-test")
            .add(user2)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarpoolTheme {
        Greeting("Android")
    }
}