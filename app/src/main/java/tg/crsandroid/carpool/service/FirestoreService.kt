package tg.crsandroid.carpool.service

import com.example.carpooling_project.model.Utilisateur
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import tg.crsandroid.carpool.database.ReservationRepo
import tg.crsandroid.carpool.database.RideRepo
import tg.crsandroid.carpool.database.UserRepo

object FirestoreService {
    val usersRepo = UserRepo()
    val ridesRepo = RideRepo()
    val reservationRepo = ReservationRepo()
    val scope = CoroutineScope(Dispatchers.IO)
    var currentUser :Utilisateur = Utilisateur()
}