package tg.crsandroid.carpool.utils
import com.example.carpooling_project.model.Utilisateur
import com.google.type.LatLng
import kotlin.math.*

// Calculer la distance
fun distanceBetween(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val earthRadius = 6371.0 // Rayon de la Terre en kilomètres

    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)

    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon / 2) * sin(dLon / 2)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return earthRadius * c // Distance en kilomètres
}

// Trouver les conducteurs les plus proches
fun findNearbyDrivers(
    userLocation: LatLng,
    destination: LatLng,
    drivers: List<Utilisateur>,
    maxDistanceKm: Double = 5.0
): List<Utilisateur> {
    return drivers.filter { driver ->
        driver.location?.let {
            distanceBetween(
                userLocation.latitude, userLocation.longitude,
                it.latitude, driver.location.longitude
            )
        }!! <= maxDistanceKm
    }
}



