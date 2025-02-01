package tg.crsandroid.carpool.presentation.screens.Map

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import tg.crsandroid.carpool.service.userDetails

object Constants {
    const val LOCATION_PERMISSION_REQUEST_CODE = 1
}

@Composable
fun MapScreen(modifier: Modifier = Modifier, defaultLocation: LatLng = LatLng(6.1725, 1.2314)) { // Default to Paris, France
    val context = LocalContext.current
    val activity = context as? Activity
    val properties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.TERRAIN // Standard, SATELLITE, TERRAIN, HYBRID
            )
        )
    }
    requireNotNull(activity) { "MapScreen must be hosted in an Activity" }


    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var userDestination by remember { mutableStateOf<LatLng?>(null) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation ?: defaultLocation, 15f)
    }

    // Gestion des permissions
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            retrieveUserLocation(context, activity) { location ->
                userLocation = location
                cameraPositionState.position = CameraPosition.fromLatLngZoom(location, 15f)
            }
        } else {
            Toast.makeText(context, "Permission de localisation refusée", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
        } else {
            retrieveUserLocation(context, activity) { location ->
                userLocation = location
                cameraPositionState.position = CameraPosition.fromLatLngZoom(location, 15f)
            }
        }
    }

    GoogleMap(
        modifier = modifier
            .fillMaxSize(),
        properties = properties,
        cameraPositionState = cameraPositionState,
        onMyLocationButtonClick = {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                retrieveUserLocation(context, activity) { location ->
                    userLocation = location
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(location, 15f)
                }
            }
            true
        },
        onMapLongClick = { latLng ->
            userDetails.userDestination = latLng
            userDestination = latLng
            // cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
        }
    ) {
        userLocation?.let { location ->
            Marker(
                state = MarkerState(location),
                title = "Votre position"
            )
            userDetails.userLocation = location
            Log.i("MAPSCREEN", "User location ${userLocation}")
        }
        Log.i("MAPSCREEN", "Map clicked ${userDestination}")
        userDestination?.let { destination ->
            Marker(
                state = MarkerState(destination),
                title = "Destination sélectionnée",
                snippet = "Lat: ${destination.latitude}, Lng: ${destination.longitude}"
            )
        }
    }
}

private fun retrieveUserLocation(context: android.content.Context, activity: Activity, onLocationRetrieved: (LatLng) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        requestLocationPermissions(activity)
        return
    }

    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
        .setWaitForAccurateLocation(true)
        .setMinUpdateIntervalMillis(5000)
        .setMaxUpdateDelayMillis(10000)
        .build()

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation
            location?.let {
                onLocationRetrieved(LatLng(it.latitude, it.longitude))
                fusedLocationClient.removeLocationUpdates(this) // Stop updates once we get a precise location
            }
        }
    }

    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
}

private fun requestLocationPermissions(activity: Activity) {
    ActivityCompat.requestPermissions(
        activity,
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
        Constants.LOCATION_PERMISSION_REQUEST_CODE
    )
}