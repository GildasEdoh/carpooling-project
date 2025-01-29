package tg.crsandroid.carpool.presentation.screens.home

import android.print.PrintAttributes.Margins
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
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
                .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { }
        ) {
            RideOptionsCard()
        }

        // Bottom Navigation Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .zIndex(2f)
                .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { }
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
                isSelected = true
            )
            NavigationItem(
                icon = Icons.Filled.DateRange,
                label = "Historque",
                isSelected = false
            )
            NavigationItem(
                icon = Icons.Filled.Email,
                label = "Chat",
                isSelected = false
            )
            NavigationItem(
                icon = Icons.Filled.Person,
                label = "Profil",
                isSelected = false
            )
        }
    }
}

@Composable
fun NavigationItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean
) {
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
fun RideOptionsCard() {
    Box(
//        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.35f)
            .zIndex(100f)
            .padding(16.dp)
            .background(Color(0x00F1F2F5)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            horizontalAlignment = CenterHorizontally
        ) {
                Column(modifier = Modifier

                ) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = { /* Handle Input */ },
                        placeholder = { Text("Entrez le lieu de départ", fontWeight = FontWeight.Normal, fontFamily = poppinsFontFamily) },
                        shape = RoundedCornerShape(topStart = 15.dp,  topEnd = 15.dp, bottomEnd = 0.dp, bottomStart = 0.dp),
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
                            focusedBorderColor = Color.Transparent

                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = "",
                        onValueChange = { /* Handle Input */ },
                        placeholder = { Text("Entrez le lieu d'arrivée", fontWeight = FontWeight.Normal, fontFamily = poppinsFontFamily) },
                        shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp, topStart = 0.dp, topEnd = 0.dp),
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
                            focusedBorderColor = Color.Transparent
                        )
                    )
                }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(16.dp) // Espacement ajouté ici
            ) {
                Button(
                    onClick = { /* Handle Take A Ride */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF001AB7),
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(4.dp),
                    shape = RoundedCornerShape(10.dp, 0.dp, 0.dp,  10.dp)
                ) {
                    Text("Prendre un Trajet", fontWeight = FontWeight.Normal, fontFamily = poppinsFontFamily)
                }

                OutlinedButton(
                    onClick = { /* Handle Offer A Ride */ },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFFF1F2F5))
                        .height(48.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFF001AB7)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF001AB7)),
                    shape = RoundedCornerShape(0.dp, 10.dp, 10.dp,  0.dp)
                ) {
                    Text("Offrie un Trajet", fontWeight = FontWeight.Normal, fontFamily = poppinsFontFamily)
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}