package tg.crsandroid.carpool.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import tg.crsandroid.carpool.model.Trajet
import tg.crsandroid.carpool.service.FirestoreService
import tg.crsandroid.carpool.ui.theme.poppinsFontFamily

class FieldsView {
}
// Contient les champs et les bouttons  du home

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideOptionsCardPassenger() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    var depart by remember { mutableStateOf("") }
    var arrivee by remember { mutableStateOf("") }
    var heureDepart by remember { mutableStateOf("") }
    var heureArrivee by remember { mutableStateOf("") }
    var nbrSeats by remember { mutableStateOf("") }
    var prix by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) } // Contrôle du dialog
    var trajetToConfirm: Trajet? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    var startRides by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .zIndex(100f)
            .padding(8.dp)
            .background(Color(0x00F1F2F5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            if (selectedIndex == 0) {
            Column(modifier = Modifier) {
                    OutlinedTextField(
                        value = depart,
                        onValueChange = { depart = it },
                        placeholder = { Text("Entrez la destination", fontWeight = FontWeight.Normal, fontFamily = poppinsFontFamily) },
                        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF1F2F5))
                            .height(50.dp),
                        textStyle = TextStyle(
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color(0xE6C7C7CB),
                            focusedBorderColor = Color(0xFF001AB7)
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Champs supplémentaires pour l'offre de trajet
            // Creation de trajets
            if (selectedIndex == 1) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = depart,
                        onValueChange = { depart = it },
                        placeholder = { Text("Entrez le lieu de depart", fontWeight = FontWeight.Normal, fontFamily = poppinsFontFamily) },
                        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF1F2F5))
                            .height(50.dp),
                        textStyle = TextStyle(
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color(0xE6C7C7CB),
                            focusedBorderColor = Color(0xFF001AB7)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = arrivee,
                        onValueChange = { arrivee = it },
                        placeholder = { Text("Entrez le lieu d'arrivée", fontWeight = FontWeight.Normal, fontFamily = poppinsFontFamily) },
                        shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF1F2F5))
                            .height(50.dp),
                        textStyle = TextStyle(
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color(0xE6C7C7CB),
                            focusedBorderColor = Color(0xFF001AB7)
                        )
                    )


                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField (
                        value = nbrSeats,
                        onValueChange = { nbrSeats = it },
                        placeholder = { Text("Nombre de places", fontFamily = poppinsFontFamily) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF1F2F5))
                            .height(50.dp),
                        shape = RoundedCornerShape(15.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color(0xE6C7C7CB),
                            focusedBorderColor = Color(0xFF001AB7)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = prix,
                        onValueChange = { prix = it },
                        placeholder = { Text("Prix", fontFamily = poppinsFontFamily) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF1F2F5))
                            .height(50.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color(0xE6C7C7CB),
                            focusedBorderColor = Color(0xFF001AB7)
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Column (
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { selectedIndex = 0 },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedIndex == 0) Color(0xFF001AB7) else Color.White,
                            contentColor = if (selectedIndex == 0) Color.White else Color(0xFF001AB7)
                        ),
                        elevation = ButtonDefaults.buttonElevation(if (selectedIndex == 0) 4.dp else 0.dp),
                        border = BorderStroke(if (selectedIndex == 0) 0.dp else 1.dp, Color(0xFF001AB7)),
                        shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                    ) {
                        Text("Prendre un Trajet", fontWeight = FontWeight.Normal, fontFamily = poppinsFontFamily)
                    }

                    Button(
                        onClick = { selectedIndex = 1 },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedIndex == 1) Color(0xFF001AB7) else Color.White,
                            contentColor = if (selectedIndex == 1) Color.White else Color(0xFF001AB7)
                        ),
                        elevation = ButtonDefaults.buttonElevation(if (selectedIndex == 1) 4.dp else 0.dp),
                        border = BorderStroke(if (selectedIndex == 1) 0.dp else 1.dp, Color(0xFF001AB7)),
                        shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                    ) {
                        Text("Offrir un Trajet", fontWeight = FontWeight.Normal, fontFamily = poppinsFontFamily)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .zIndex(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { }
                ){
                    // Boutton de validation
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(15.dp),
                        onClick = {
                            Log.i("Home clicl", "Clic sur valider number: ${selectedIndex}")
                            if (selectedIndex == 1) { // Creation de trajet par le conducteur
                                trajetToConfirm = Trajet(
                                    "1",
                                    lieuDepart = depart,
                                    lieuArrivee = arrivee,
                                    heureDepart = heureDepart,
                                    heureArrivee =heureArrivee,
                                    duree = "",
                                    nbrSeats = nbrSeats,
                                    idConducteur = FirestoreService.currentUser.id!!,
                                    prix = 500.toString(),
                                )
                                FirestoreService.currentUser.type = "Conducteur"
                                showDialog = true
                            } else { // Prise de trajet par l'utilisateur
                                startRides = true
                                FirestoreService.currentUser.type = "Passager"
                            }
                        },
                        enabled = true,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF001AB7),
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                        border = null,
                        contentPadding = PaddingValues(),
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        Text("Valider", fontFamily = poppinsFontFamily, color = Color.White)
                    }

                    // Affichage du dialogue de confirmation
                    Log.i("MMMMMMM", "Showpanel ${showDialog}, ${trajetToConfirm}")
                    if (showDialog && trajetToConfirm != null) {
                        showDialogPanel(trajetToConfirm!!, context)
                        showDialog = false
                    }
                    // Affiche la liste des trajets
                    if (startRides) {
                        DisplayRideList()
                        startRides = false
                    }
                }
            }
            // Boutons de sélection
        }
    }
}