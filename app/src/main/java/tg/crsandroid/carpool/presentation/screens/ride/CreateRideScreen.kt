package tg.crsandroid.carpool.presentation.screens.ride

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import tg.crsandroid.carpool.presentation.screens.home.RideOptionsCardPassenger

@Composable
fun CreateRideScreen() {
    Box (modifier = Modifier
        .fillMaxSize()
    ) {
        Column {
            // CreateRideForm()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(bottom = 80.dp) // Ajoute de l’espace
                    .zIndex(1f)
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { }
            ) {
                RideOptionsCardPassenger()
            }
        }

    }
}