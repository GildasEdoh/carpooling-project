package tg.crsandroid.carpool.service

import android.util.Log
import com.example.carpooling_project.model.Utilisateur
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.xmlpull.v1.sax2.Driver
import tg.crsandroid.carpool.database.ReservationRepo
import tg.crsandroid.carpool.database.RideRepo
import tg.crsandroid.carpool.database.UserRepo
import tg.crsandroid.carpool.model.Trajet
import tg.crsandroid.carpool.service.FirestoreService.scope

object FirestoreService {
    val usersRepo = UserRepo()
    val ridesRepo = RideRepo()
    val reservationRepo = ReservationRepo()
    val scope = CoroutineScope(Dispatchers.IO)
    var currentUser :Utilisateur = Utilisateur()
    var trajets: List<Trajet>? = null
    var idY: String? = null
}

object userDetails {
    var userDestination: LatLng? = null
    var userLocation: LatLng? = null
    fun insertRidesIntoDb(drivers: List<Trajet>, callback: (Boolean) -> Unit) {
        for (elt in drivers) {
            launchSuspendFunction(scope, callback) {
                FirestoreService.ridesRepo.addDocument(elt)
            }
        }
    }
    fun getNearbyUser(lng: LatLng) {

    }

    fun getAllRiders(scope: CoroutineScope, callback: (List<Trajet>) -> Unit) {
        scope.launch {
            try {
                val rides = FirestoreService.ridesRepo.getAllDocuments() as List<Trajet>
                Log.i("Firestore service", "Taille : ${rides.size}")
                callback(rides) // Succès avec la liste
            } catch (e: Exception) {
                Log.e("Firestore service", "Erreur : ${e.message}")
                callback(emptyList()) // Retourne une liste vide en cas d'erreur
            }
        }
    }

    fun launchSuspendFunction(scope: CoroutineScope, callback: (Boolean) -> Unit, suspendFunction: suspend () -> Boolean) {
        scope.launch {
            val result = suspendFunction()
            callback(result)
        }
    }
}