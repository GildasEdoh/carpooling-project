package tg.crsandroid.carpool.presentation.screens.historique

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tg.crsandroid.carpool.R

data class RideHistory(
    val name: String,
    val rating:Double = 0.0,
    val status: String,
    val startLocation: String,
    val endLocation: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController, rides: List<RideHistory>, onBackClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {

        // En-tête
        TopAppBar(
            title = { Text("Historique", color=Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Retour",
                    )
                }
            }
        )

        // Liste des trajets
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(rides) { ride ->
                RideItem(ride)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

// Élément d'un trajet individuel
@Composable
fun RideItem(ride: RideHistory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image de profil
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 12.dp),
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Nom
                Text(
                    text = ride.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                //Note
                if (ride.rating > 0.0) {
                    Text(
                        text = "${ride.rating} ★",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                // Adresse
                Text(
                    text = "de: ${ride.startLocation}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "à: ${ride.endLocation}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                // Badge de statut
                Status(status = ride.status)

                // Icône de discussion seulement pour "In Progress"
                if (ride.status == "In Progress..") {
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.chat),
                        contentDescription = "Chat Icon",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Status(status: String) {
    val backgroundColor = when (status) {
        "In Progress.." -> Color(0xFF99BFE8)
        else -> Color(0xB0147325)
    }
    val textColor = Color(0xFF000000)

    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = status,
            fontSize = 12.sp,
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
// Aperçu de l'écran
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHistoryScreen() {
    /*val sampleRides = listOf(
        RideHistory("Jane Doe", 3.0,"In Progress..", "Atakpamé", "Dapaong"),
        RideHistory("Jane Doe", 3.0,"In Progress..", "Atakpamé", "Dapaong") ,
        RideHistory("John Doe", 4.5,"Completed", "Lomé", "Kara"),
        RideHistory("John Doe", 4.5,"Completed", "Lomé", "Kara"),
        RideHistory("John Doe", 4.5,"Completed", "Lomé", "Kara"),

    )
    HistoryScreen(
        rides = sampleRides,
        onBackClick = {},
    )*/
}
