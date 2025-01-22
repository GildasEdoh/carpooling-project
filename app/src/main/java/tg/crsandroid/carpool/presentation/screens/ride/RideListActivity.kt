package tg.crsandroid.carpool.presentation.screens.ride

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import androidx.navigation.ui.AppBarConfiguration
import tg.crsandroid.carpool.databinding.ActivityRideListScreenBinding
import tg.crsandroid.carpool.model.Trajet

class RideListActivity : ComponentActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    // private lateinit var binding: ActivityRideListScreenBinding

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding = ActivityRideListScreenBinding.inflate(layoutInflater)
        // setContentView(binding.root)
        // setSupportActionBar(binding.toolbar)
        setContent {
            MaterialTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Liste des Trajets") },
                            navigationIcon = {
                                IconButton(onClick = { /* Action de retour */ }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack, // Icône de retour
                                        contentDescription = "Retour"
                                    )
                                }
                            }
                        )
                    }
                )  { paddingValues -> // Paramètre pour récupérer le padding fourni par Scaffold
                    Box(
                        modifier = Modifier.padding(paddingValues) // Applique le padding
                    ) {
                        RideListScreen() // Contenu principal
                    }
                }
            }
        }

    }
    @Composable
    fun RideListScreen() {
        val trajet1 = Trajet("1", "lome", "kara", "14h30", "13h20",
            "10h", "5", "1", emptyList()
        )
        val trajet2 = Trajet("2", "lome", "kara", "14h30", "13h20",
            "10h", "5", "1", emptyList()
        )
        val trajet3 = Trajet("3", "lome", "kara", "14h30", "13h20",
            "10h", "5", "1", emptyList()
        )
        val trajet4 = Trajet("4", "lome", "kara", "14h30", "13h20",
            "10h", "5", "1", emptyList()
        )
        val trajet5 = Trajet("5", "lome", "kara", "14h30", "13h20",
            "10h", "5", "1", emptyList()
        )
        val trajet6 = Trajet("6", "lome", "kara", "14h30", "13h20",
            "10h", "5", "1", emptyList()
        )
        val trajet7 = Trajet("7", "lome", "kara", "14h30", "13h20",
            "10h", "5", "1", emptyList()
        )
        val trajet8 = Trajet("8", "lome", "kara", "14h30", "13h20",
            "10h", "5", "1", emptyList()
        )

        var trajets = listOf<Trajet>(
            trajet1,
            trajet2,
            trajet3,
            trajet4,
            trajet6,
            trajet5
        )
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            items(trajets) { trajet ->
                TrajetElement(trajet)
            }
        }
    }
    @Composable
    fun TrajetElement(trajet: Trajet) {
        val color = Color(0xFF2196F3) // Code couleur pour un bleu spécifique
        val buttonColor = Color(0xFF4C22AD)
        Card(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) { // La première colonne prend plus d'espace
                        Text(
                            text = "${trajet.lieuDepart} ---> ${trajet.lieuArrivee}",
                            style = MaterialTheme.typography.headlineLarge,
                            color = color
                        )
                        Text(
                            text = "${trajet.heureDepart} à ${trajet.heureArrivee}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "${trajet.heureDepart}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp)) // Espacement entre les colonnes
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Places: ${trajet.nbrSeats}",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp)) // Espacement avant le bouton
                Button(
                    onClick = {
                        reserverTrajet(trajet)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                ) {
                    Text(text = "Réserver")
                }
            }
        }
    }
    fun reserverTrajet(trajet: Trajet) {
        Log.i("RideListActivity ", "Rseverver trajet : ${trajet.id}")
    }

}
