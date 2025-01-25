package tg.crsandroid.carpool.database

import com.example.carpooling_project.model.Utilisateur
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class UserRepo : BaseRepository<Utilisateur>("utilisateurs", Utilisateur::class.java) {
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
}