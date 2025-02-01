package tg.crsandroid.carpool.utils
import androidx.compose.runtime.Composable
import com.example.carpooling_project.model.Utilisateur
import com.google.android.gms.maps.model.LatLng
import tg.crsandroid.carpool.model.Trajet
import kotlin.math.*
import kotlin.random.Random

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

fun generateRandomLatLng(baseLat: Double, baseLng: Double, maxDistanceKm: Double): LatLng {
    val earthRadius = 6371.0 // Rayon de la Terre en kilomètres

    val random = Random(System.currentTimeMillis())
    val distance = random.nextDouble(0.0, maxDistanceKm) / earthRadius // Distance aléatoire en radians
    val angle = random.nextDouble(0.0, 2 * Math.PI) // Angle aléatoire en radians

    val deltaLat = distance * Math.cos(angle)
    val deltaLng = distance * Math.sin(angle) / Math.cos(Math.toRadians(baseLat))

    val newLat = baseLat + Math.toDegrees(deltaLat)
    val newLng = baseLng + Math.toDegrees(deltaLng)

    return LatLng(newLat, newLng)
}

@Composable
fun generateTrajets(baseLat: Double, baseLng: Double, count: Int): List<Trajet> {
    val lieuxDepart = listOf("Lomé", "Atakpamé", "Kpalimé", "Aného", "Sokodé", "cinkasse", "tabligbo")
    val lieuxArrivee = listOf("Kara", "Dapaong", "Tsévié", "Vogan", "Cinkassé", "ul")
    val heuresDepart = listOf("08:00", "09:00", "10:00", "11:00", "12:00")
    val heuresArrivee = listOf("12:00", "13:00", "14:00", "15:00", "16:00")
    val durees = listOf("4h", "3h", "2h", "1h30", "1h")
    val prixRange = 2000..10000
    val seatsRange = 1..4
    val conducteurs = listOf("100660489203576952020", "102543517565443919020", "100660489203576952020")

    return List(count) {
        val randomLatLng = generateRandomLatLng(baseLat, baseLng, 10.0)
        Trajet(
            lieuDepart = lieuxDepart.random(),
            lieuArrivee = lieuxArrivee.random(),
            heureDepart = heuresDepart.random(),
            heureArrivee = heuresArrivee.random(),
            duree = durees.random(),
            prix = (prixRange.random()).toString(),
            nbrSeats = seatsRange.random().toString(),
            idConducteur = conducteurs.random(),
            destination = randomLatLng
        )
    }
}


