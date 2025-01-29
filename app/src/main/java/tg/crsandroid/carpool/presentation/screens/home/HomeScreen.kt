package tg.crsandroid.carpool.presentation.screens.home

import android.print.PrintAttributes.Margins
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import tg.crsandroid.carpool.presentation.screens.Map.MapScreen
import tg.crsandroid.carpool.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Box {
        Scaffold(
            Modifier.background(Color.Transparent),
            bottomBar = { BottomNavigationBar() }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier

                ) {

                    MapScreen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                    // Ride options card
                    RideOptionsCard()
                }
            }
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
                label = "Home",
                isSelected = true
            )
            NavigationItem(
                icon = Icons.Filled.DateRange,
                label = "History",
                isSelected = false
            )
            NavigationItem(
                icon = Icons.Filled.Email,
                label = "Chat",
                isSelected = false
            )
            NavigationItem(
                icon = Icons.Filled.Person,
                label = "Profile",
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
        horizontalAlignment = Alignment.CenterHorizontally,
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

@Composable
fun RideOptionsCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(modifier = Modifier
                    .weight(1f)
                ) {
                    // Pickup location input
                    OutlinedTextField(
                        value = "",
                        onValueChange = { /* Handle Input */ },
                        placeholder = { Text("Enter Pickup Location") },
                        shape = RoundedCornerShape(topStart = 10.dp,  topEnd = 10.dp, bottomEnd = 0.dp, bottomStart = 0.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp),
                    )

                    Spacer(modifier = Modifier.width(12.dp))
                    // Drop location input

                    OutlinedTextField(
                        value = "",
                        onValueChange = { /* Handle Input */ },
                        placeholder = { Text("Enter Drop Location") },
                        shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp, topStart = 0.dp, topEnd = 0.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp),
                    )
                }

                // Time Icon
//                IconButton(
//                    onClick = { /* Handle Time Click */ },
//                    modifier = Modifier.padding(start = 8.dp)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Lock,
//                        contentDescription = "Set Time",
//                        tint = Color(0xFF3C52C5)
//                    )
//                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /* Handle Take A Ride */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C52C5)),
                    elevation = ButtonDefaults.buttonElevation(8.dp)
                ) {
                    Text("Take A Ride", color = Color.White)
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    onClick = { /* Handle Offer A Ride */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF3C52C5)),
                    border = BorderStroke(1.dp, Color(0xFF3C52C5))
                ) {
                    Text("Offer A Ride")
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