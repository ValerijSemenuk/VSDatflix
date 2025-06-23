package com.datflix.repositories

import com.datflix.models.Cast
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CastRepository : Repository<Cast, String> {

    private val firestore: Firestore = FirestoreClient.getFirestore()
    private val collection = firestore.collection("cast")

    override suspend fun getAll(): List<Cast> = withContext(Dispatchers.IO) {
        collection.get().get().documents.mapNotNull { doc ->
            doc.toObject(Cast::class.java)?.copy(id = doc.id)
        }
    }

    override suspend fun getById(id: String): Cast? = withContext(Dispatchers.IO) {
        val doc = collection.document(id).get().get()
        if (doc.exists()) doc.toObject(Cast::class.java)?.copy(id = doc.id) else null
    }

    override suspend fun create(cast: Cast): Cast = withContext(Dispatchers.IO) {
        val docRef = collection.document()
        val castWithId = cast.copy(id = docRef.id)
        docRef.set(castWithId).get()
        castWithId
    }

    override suspend fun update(cast: Cast): Boolean = withContext(Dispatchers.IO) {
        val id = cast.id ?: return@withContext false
        val docRef = collection.document(id)
        if (!docRef.get().get().exists()) return@withContext false
        docRef.set(cast).get()
        true
    }

    override suspend fun delete(id: String): Boolean = withContext(Dispatchers.IO) {
        collection.document(id).delete().get()
        true
    }
}
