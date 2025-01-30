package tg.crsandroid.carpool.presentation.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.navigation.compose.rememberNavController
import tg.crsandroid.carpool.NavigationRoute.Home.route
import tg.crsandroid.carpool.presentation.screens.Map.MapScreen
import tg.crsandroid.carpool.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Map interactive en arrière-plan
        MapScreen(
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 45.dp)
                .zIndex(1f)

        ){
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.Center,
                text = "Carpool",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = Color(0xFF1A33CE),
           )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp) // Ajoute de l’espace
                .zIndex(1f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { }
        ) {
            RideOptionsCardPassenger()
        }



        // Bottom Navigation Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .zIndex(2f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { }
        ) {
            BottomNavigationBar()
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun LocationInputField(hint: String) {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(hint) },
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun BottomNavigationBar() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()


    Box(
        modifier = Modifier
            .fillMaxWidth()
//            .height(72.dp)
            .padding(16.dp)
            .zIndex(100f)

            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFF001AB7)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationItem(
                icon = Icons.Filled.Home,
                label = "Accueil",
                isSelected = selectedIndex == 0,
                onClick = { selectedIndex = 0
                    navController.navigate(route)
                }
            )

            NavigationItem(
                icon = Icons.Filled.DateRange,
                label = "Historique",
                isSelected = selectedIndex == 1,
                onClick = { selectedIndex = 1
                    navController.navigate(route)
                }
            )

            NavigationItem(
                icon = Icons.Filled.Email,
                label = "Chat",
                isSelected = selectedIndex == 2,
                onClick = { selectedIndex = 2
                    navController.navigate(route)
                }
            )

            NavigationItem(
                icon = Icons.Filled.Person,
                label = "Profil",
                isSelected = selectedIndex == 3,
                onClick = { selectedIndex = 3
                    navController.navigate(route)
                }
            )
        }
    }
}

@Composable
fun NavigationItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable(onClick = onClick)

    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) Color.White else Color(0xFFB0C4FF),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = if (isSelected) Color.White else Color(0xFFB0C4FF)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideOptionsCardPassenger() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    var depart by remember { mutableStateOf("") }
    var arrivee by remember { mutableStateOf("") }
    var heureDepart by remember { mutableStateOf("") }
    var heureArrivee by remember { mutableStateOf("") }
    var nbrSeats by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .zIndex(100f)
            .padding(16.dp)
            .background(Color(0x00F1F2F5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            // Champs communs
            Column(modifier = Modifier) {
                OutlinedTextField(
                    value = depart,
                    onValueChange = { depart = it },
                    placeholder = { Text("Entrez le lieu de départ", fontWeight = FontWeight.Normal, fontFamily = poppinsFontFamily) },
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

                Spacer(modifier = Modifier.height(4.dp))

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

            }

            Spacer(modifier = Modifier.height(16.dp))

            // Champs supplémentaires pour l'offre de trajet
            if (selectedIndex == 1) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = heureDepart,
                        onValueChange = { heureDepart = it },
                        placeholder = { Text("Heure de départ", fontFamily = poppinsFontFamily) },
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
                            .height(48.dp),
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
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(15.dp),
                        onClick = { /* Handle click */ },
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
                }
            }

            // Boutons de sélection
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

