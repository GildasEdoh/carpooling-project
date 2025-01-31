package tg.crsandroid.carpool.database

import com.example.carpooling_project.model.Utilisateur
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.type.LatLng
import kotlinx.coroutines.tasks.await
import tg.crsandroid.carpool.utils.distanceBetween

class UserRepo : BaseRepository<Utilisateur>("users", Utilisateur::class.java) {
    private val firestore: FirebaseFirestore = Firebase.firestore

    suspend fun addUser(user: Utilisateur): Boolean {
        return try {
            firestore.collection(this.collectionName)
                .document(user.id!!)
                .set(user)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getAllConductors(): List<Utilisateur> {
        return try {
            val querySnapshot: QuerySnapshot = firestore.collection(collectionName)
                .whereEqualTo("type", "Conducteur") // Filtre pour les utilisateurs de type conducteur
                .get()
                .await()

            querySnapshot.documents.mapNotNull { it.toObject(Utilisateur::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun findNearbyDrivers(
        userLocation: LatLng,
        destination: LatLng,
        drivers: List<Utilisateur>,
        maxDistanceKm: Double = 5.0
    ): List<Utilisateur> {
        return drivers.filter { driver ->
            distanceBetween(
                userLocation.latitude, userLocation.longitude,
                driver.location!!.latitude, driver.location.longitude
            ) <= maxDistanceKm
        }
    }
}