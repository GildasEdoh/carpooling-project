package tg.crsandroid.carpool.presentation.screens.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tg.crsandroid.carpool.DashActivity
import tg.crsandroid.carpool.R
import tg.crsandroid.carpool.model.Reservation
import tg.crsandroid.carpool.model.Trajet
import tg.crsandroid.carpool.service.FirestoreService
import tg.crsandroid.carpool.service.FirestoreService.scope
import java.time.LocalDate

// Contient la logique pour enregistrer les donnes dans la base de donnee

class Dialog {
}
fun launchSuspendFunction(scope: CoroutineScope, callback: (Boolean) -> Unit, suspendFunction: suspend () -> Boolean) {
    scope.launch {
        val result = suspendFunction()
        callback(result)
    }
}

// Dialogue de confirmation
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
                text = "Vous allez creer un trajet partant de ${trajet.lieuDepart} à ${trajet.lieuArrivee} pour ${trajet.prix} FCFA.",
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
                        containerColor = Color(0xFF001AB7),
                        contentColor = Color.White
                    )
                ) {
                    Text("Confirmer")
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    // val navController :NavController = NavController(MainActivity)
    // HomeScreen(navController)
}

@Composable
fun showDialogPanel(trajetToConfirm: Trajet, context: Context) {
    var showDialog by remember { mutableStateOf(true) }
    if (showDialog) {
        ConfirmationDialog(
            trajet = trajetToConfirm,
            onDismiss = {
                Log.i("HOMESCREEN", "ANNULE")
                showDialog = false
            },
            onConfirm = {
                createTrajet(trajetToConfirm) { isSuccess ->
                    if (isSuccess) {
                        Log.i("RideOptionsCardPassenger", "Trajet créé avec succès")
                    } else {
                        Log.i("RideOptionsCardPassenger", "Erreur lors de la création du trajet")
                    }
                }
                showDialog = false

            }
        )
    }

}
fun createTrajet(trajet: Trajet, callback: (Boolean) -> Unit) {
    launchSuspendFunction(scope, callback) {
        FirestoreService.ridesRepo.addDocument(trajet)
    }
}

// Passer a l'affichage de la liste des trajets
@Composable
fun DisplayRideList(navController: NavController) {
    /*val context = LocalContext.current
    val intent = Intent(context, RideListActivity::class.java)
    context.startActivity(intent)*/
    navController.navigate("listeTrajets") {
        popUpTo(navController.graph.startDestinationId)
        launchSingleTop = true
    }
}