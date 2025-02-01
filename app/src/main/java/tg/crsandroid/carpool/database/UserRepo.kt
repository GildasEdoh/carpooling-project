package tg.crsandroid.carpool.database

import com.example.carpooling_project.model.Utilisateur
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.type.LatLng
import kotlinx.coroutines.tasks.await
import tg.crsandroid.carpool.model.Trajet
import tg.crsandroid.carpool.utils.distanceBetween

class UserRepo : BaseRepository<Utilisateur>("users", Utilisateur::class.java) {
    private val firestore: FirebaseFirestore = Firebase.firestore

    suspend fun addUser(user: Utilisateur) : Boolean{
        if (checkUserExists(userId = user.id!!)) {
            return setUser(user)
        }
        return addDocument(user)
    }

    suspend fun setUser(user: Utilisateur) : Boolean{
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
    suspend fun checkUserExists(userId: String): Boolean {
        return try {
            val document = firestore.collection(this.collectionName)
                .document(userId)
                .get()
                .await()
            document.exists()
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
    // Retourne un document par son id
    suspend fun getUserByRide(trajet: Trajet): Utilisateur? { // drivers
        return try {
            val documentSnapshot = firestore.collection(collectionName)
                .document(trajet.idConducteur)
                .get()
                .await()
            documentSnapshot.toObject(modelClass)
        } catch (e: Exception) {
            null
        }
    }
}