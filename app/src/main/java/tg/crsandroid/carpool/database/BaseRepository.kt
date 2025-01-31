package tg.crsandroid.carpool.database

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

open class BaseRepository <T: Any> (val collectionName: String, val modelClass: Class<T>) {
    private val firestore: FirebaseFirestore = Firebase.firestore

    suspend fun addDocument(document : T): Boolean {
        return try {
            firestore.collection(collectionName)
                .add(document)
                .await()
            true
        } catch (e : Exception) {
            false
        }
    }

    suspend fun updateDocument(documentId: String, updatedData: Map<String, Any>) : Boolean{
        return try {
            firestore.collection(collectionName)
                .document(documentId)
                .update(updatedData)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Supprimer un document par son ID
    suspend fun deleteDocument(documentId: String): Boolean {
        return try {
            firestore.collection(collectionName)
                .document(documentId)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Récupérer tous les documents
    suspend fun getAllDocuments(): List<T> {
        return try {
            val querySnapshot: QuerySnapshot = firestore.collection(collectionName)
                .get()
                .await()
            querySnapshot.documents.mapNotNull { it.toObject(modelClass) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Retourne un document par son id
    suspend fun getDocumentById(documentId: String): T? {
        return try {
            val documentSnapshot = firestore.collection(collectionName)
                .document(documentId)
                .get()
                .await()
            documentSnapshot.toObject(modelClass)
        } catch (e: Exception) {
            null
        }
    }
    fun addDocumentFromNonCoroutine(document: T, scope: CoroutineScope, callback: (Boolean) -> Unit) {
        scope.launch {
            val isSuccess = addDocument(document)
            callback(isSuccess)
        }
    }
    fun launchSuspendFunction(scope: CoroutineScope, callback: (Boolean) -> Unit, suspendFunction: suspend () -> Boolean) {
        scope.launch {
            val result = suspendFunction()
            callback(result)
        }
    }

}