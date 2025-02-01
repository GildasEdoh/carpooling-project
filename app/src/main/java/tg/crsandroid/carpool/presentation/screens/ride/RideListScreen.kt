package tg.crsandroid.carpool.presentation.screens.ride

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.carpooling_project.model.Utilisateur
import kotlinx.coroutines.delay
import tg.crsandroid.carpool.R
import tg.crsandroid.carpool.model.Reservation
import tg.crsandroid.carpool.model.Trajet
import tg.crsandroid.carpool.service.FirestoreService
import tg.crsandroid.carpool.service.userDetails
import tg.crsandroid.carpool.utils.findNearbyDrivers
import java.time.LocalDate


class RideListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF3C52C5),
                    secondary = Color(0xFF3C52C5),
                    surface = Color(0xFFFFFFFF)
                )
            ) {
                RideListScreen(
                    onBackPressed = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideListScreen(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTrajet by remember { mutableStateOf<Trajet?>(null) }
    // State pour gérer les trajets et l'état de chargement
    var trajets = remember { mutableStateOf<List<Trajet>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) } // Indicateur de chargement

    // Récupérer les trajets depuis Firestore dans un LaunchedEffect
    LaunchedEffect(Unit) {
        // Appeler la fonction suspendue dans une coroutine
        try {
            trajets.value = getAllTrajets() // Appeler la fonction suspendue ici
            Log.i("REUSIIS JJJJJJJJJJJJ", "JJJJJJJJJJ ----- ----- -------")
        } catch (e: Exception) {
            Log.i("ERREUR", "ERERUUR ----- ----- -------")
            trajets.value = emptyList() // En cas d'erreur, retourner une liste vide
        }
        isLoading.value = false // Changer l'état de chargement une fois la récupération terminée
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Trajets Disponibles",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.headlineSmall,
                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Retour",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            // Vérifier si les données sont encore en train de charger
            if (isLoading.value) {
                Log.i("LISTE_____", "Chargement en cours")
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center) // Center l'indicateur
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else {
                Log.i("LISTE_____", "Chargement fini: " + trajets.value.size)
                // Si les trajets sont récupérés, on les affiche
                if (userDetails.userDestination != null) {
                    val near = findNearbyDrivers(userDetails.userLocation!!, userDetails.userDestination!!, trajets.value, 5.0)
                    // trajets.value = near
                    Log.i("RideList", "Near : ${near}")
                }
                items(trajets.value) { trajet ->
                    TrajetCard(
                        trajet = trajet,
                        onReservationClick = { /* logique pour réserver un trajet */ }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
    }
    selectedTrajet?.let { trajet ->
        ConfirmationDialog(
            trajet = trajet,
            onDismiss = { selectedTrajet = null },
            onConfirm = {
            // eserverTrajet(trajet)
                selectedTrajet = null
            }
        )
    }
}

@Composable
private fun TrajetCard(
    trajet: Trajet,
    onReservationClick: (Trajet) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Column {
                    Text(
                        text = "${trajet.lieuDepart} - ${trajet.lieuArrivee}",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Trajet direct • ${trajet.duree}",
                        style = MaterialTheme.typography.bodyMedium,
//                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                    )
                }
            }

            Divider(color = Color(0xFFEEEEEE), thickness = 2.dp)

            // Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItem(
                    icon = Icons.Default.AccountCircle,
                    title = "Conducteur",
                    value = "EDOH"
                )
                InfoItem(
                    icon = Icons.Default.DateRange,
                    title = "Départ",
                    value = trajet.heureDepart
                )
                InfoItem(
                    icon = Icons.Default.CheckCircle,
                    title = "Arrivée",
                    value = trajet.heureArrivee
                )
                InfoItem(
                    icon = Icons.Default.AccountCircle,
                    title = "Places",
                    value = trajet.nbrSeats
                )
                InfoItem(
                    icon = Icons.Default.Info,
                    title = "Infos",
                    value = "",
                    onClick = {
                        Log.i("RIDELIST", "Liste ride")
                    }
                )
            }

            // Price & Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Prix par place",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "${trajet.prix} FCFA",
                        style = MaterialTheme.typography.headlineSmall,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = FontFamily(Font(R.font.poppins_bold))
                    )
                }

                Button(
                    onClick = { onReservationClick(trajet) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    Text(
                        text = "Réserver",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoItem(
    icon: ImageVector,
    title: String,
    value: String,
    onClick: () -> Unit = {}
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(24.dp)
                .clickable { onClick() } // Ajout de l'écouteur de clic
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = FontFamily(Font(R.font.poppins_medium))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConfirmationDialog(
    trajet: Trajet,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
    onDismissRequest = onDismiss,
    modifier = Modifier.padding(24.dp),
    properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
                .background(Color.White)
        ) {
            Text(
                text = "Confirmer la réservation",
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = FontFamily(Font(R.font.poppins_semibold))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Vous allez réserver un trajet de ${trajet.lieuDepart} à ${trajet.lieuArrivee} pour ${trajet.prix} FCFA.",
                fontSize = 14.sp,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Annuler")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onConfirm,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    )
                ) {
                    Text("Confirmer")
                }
            }
        }
    }
}

private fun reserverTrajet(trajet: Trajet) {
    val reservation = Reservation (
        idConducteur = FirestoreService.currentUser.id!!,
        date = LocalDate.now().toString(),
        idTrajet = trajet.id
    )
}

private fun generateSampleTrajets(): List<Trajet> {
    var trajets = listOf(
        Trajet(
            lieuDepart = "Lomé",
            lieuArrivee = "Kara",
            heureDepart = "08:00",
            heureArrivee = "12:00",
            duree = "4h",
            prix = 5000.toString(),
            nbrSeats = 3.toString(),
            idConducteur = "G"
        ),
        Trajet(
            lieuDepart = "Lomé",
            lieuArrivee = "Atakpamé",
            heureDepart = "09:00",
            heureArrivee = "11:00",
            duree = "2h",
            prix = 3000.toString(),
            nbrSeats = 2.toString()
        ),
        Trajet(
            lieuDepart = "Lomé",
            lieuArrivee = "Aného",
            heureDepart = "10:00",
            heureArrivee = "11:30",
            duree = "1h30",
            prix = 2000.toString(),
            nbrSeats = 1.toString(),
            idConducteur = "G"
        ),
        Trajet(
        lieuDepart = "Lomé",
        lieuArrivee = "Aného",
        heureDepart = "10:00",
        heureArrivee = "11:30",
        duree = "1h30",
        prix = 2000.toString(),
        nbrSeats = 1.toString(),
        idConducteur = "G"
        )
    )
    return trajets
}
suspend fun getAllTrajets(): List<Trajet> {
    return try {
        FirestoreService.ridesRepo.getAllDocuments() // Appel à la fonction suspendue
    } catch (e: Exception) {
        emptyList() // Retourner une liste vide en cas d'erreur
    }
}
suspend fun getUserById() {

}
fun startChat() {

}