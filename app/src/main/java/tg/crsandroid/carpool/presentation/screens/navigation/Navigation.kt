package tg.crsandroid.carpool.presentation.screens.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tg.crsandroid.carpool.presentation.screens.home.NavigationItem

@Composable
fun BottomNavigationBar(navController: NavController) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF3C52C5)),
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
                isSelected = selectedIndex == 0,
                onClick = {
                    selectedIndex = 0
                    navController.navigate("Home") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )

            NavigationItem(
                icon = Icons.Filled.DateRange,
                label = "History",
                isSelected = selectedIndex == 1,
                onClick = {
                    selectedIndex = 1
                    navController.navigate("History") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )

            NavigationItem(
                icon = Icons.Filled.Email,
                label = "Chat",
                isSelected = selectedIndex == 2,
                onClick = {
                    selectedIndex = 2
                    navController.navigate("Chat") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )

            NavigationItem(
                icon = Icons.Filled.Person,
                label = "Profil",
                isSelected = selectedIndex == 3,
                onClick = {
                    selectedIndex = 3
                    navController.navigate("Profil") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}