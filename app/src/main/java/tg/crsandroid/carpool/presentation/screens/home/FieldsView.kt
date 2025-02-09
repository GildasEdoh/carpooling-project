package tg.crsandroid.carpool.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.navigation.NavController
import tg.crsandroid.carpool.model.Trajet
import tg.crsandroid.carpool.service.FirestoreService
import tg.crsandroid.carpool.service.userDetails
import tg.crsandroid.carpool.ui.theme.poppinsFontFamily
import tg.crsandroid.carpool.utils.generateTrajets

class FieldsView {
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideOptionsCardPassenger(navController: NavController) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    var depart by remember { mutableStateOf("") }
    var arrivee by remember { mutableStateOf("") }
    var heureDepart by remember { mutableStateOf("") }
    var heureArrivee by remember { mutableStateOf("") }
    var nbrSeats by remember { mutableStateOf("") }
    var prix by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var trajetToConfirm: Trajet? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    var startRides by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .zIndex(100f)
            .padding(8.dp)
            .background(Color(0x00F1F2F5)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            if (selectedIndex == 0) {
                Column(modifier = Modifier) {
                    Text(
                        text = "Selectionnez la destination sur la carte", // La variable contenant l'information à afficher
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF1F2F5))
                            .height(50.dp)
                            .padding(vertical = 14.dp), // Ajuste la position verticale du texte
                        color = Color.Black // Couleur du texte
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

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
                            focusedBorderColor = Color(0xFF3C52C5)
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
                            focusedBorderColor = Color(0xFF3C52C5)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
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
                            focusedBorderColor = Color(0xFF3C52C5)
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
                            focusedBorderColor = Color(0xFF3C52C5)
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { selectedIndex = 0 },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedIndex == 0) Color(0xFF3C52C5) else Color.White,
                            contentColor = if (selectedIndex == 0) Color.White else Color(0xFF3C52C5)
                        ),
                        elevation = ButtonDefaults.buttonElevation(if (selectedIndex == 0) 4.dp else 0.dp),
                        border = BorderStroke(if (selectedIndex == 0) 0.dp else 1.dp, Color(0xFF3C52C5)),
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
                            containerColor = if (selectedIndex == 1) Color(0xFF3C52C5) else Color.White,
                            contentColor = if (selectedIndex == 1) Color.White else Color(0xFF3C52C5)
                        ),
                        elevation = ButtonDefaults.buttonElevation(if (selectedIndex == 1) 4.dp else 0.dp),
                        border = BorderStroke(if (selectedIndex == 1) 0.dp else 1.dp, Color(0xFF3C52C5)),
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
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        elevation = ButtonDefaults.buttonElevation(20.dp),
                        shape = RoundedCornerShape(15.dp),
                        onClick = {
                            Log.i("Home clicl", "Clic sur valider number: ${selectedIndex}")
                            if (selectedIndex == 1) {
                                trajetToConfirm = Trajet(
                                    "1",
                                    lieuDepart = depart,
                                    lieuArrivee = arrivee,
                                    heureDepart = heureDepart,
                                    heureArrivee = heureArrivee,
                                    duree = "",
                                    nbrSeats = nbrSeats,
                                    idConducteur = FirestoreService.currentUser.id!!,
                                    prix = 500.toString(),
                                )
                                FirestoreService.currentUser.type = "Conducteur"
                                showDialog = true
                            } else {
                                startRides = true
                                FirestoreService.currentUser.type = "Passager"


                            }
                        },
                        enabled = true,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF3C52C5),
                            contentColor = Color.White
                        ),
                        border = null,
                        contentPadding = PaddingValues(),
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        Text("Valider", fontFamily = poppinsFontFamily, color = Color.White)
                    }

                    Log.i("MMMMMMM", "Showpanel ${showDialog}, ${trajetToConfirm}")
                    if (showDialog && trajetToConfirm != null) {
                        showDialogPanel(trajetToConfirm!!, context)
                        showDialog = false
                    }
                    if (startRides) {
                        if (userDetails.userDestination != null) {
                           /* val trajets = generateTrajets(userDetails.userDestination!!.latitude, userDetails.userDestination!!.longitude, 25)
                            userDetails.insertRidesIntoDb(trajets) { isSucces ->
                                if (isSucces) {
                                    Log.i("TRAJEST", "TRAJETS AJOUTES")
                                } else {
                                    Log.i("TRAJEST", "ERREUR")
                                }

                            }*/
                        }
                        DisplayRideList(navController)
                        startRides = false
                    }
                }
            }
        }
    }
}