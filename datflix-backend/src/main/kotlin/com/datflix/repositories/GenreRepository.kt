package com.datflix.repositories

import com.datflix.models.Genre
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenreRepository : Repository<Genre, String> {

    private val firestore: Firestore = FirestoreClient.getFirestore()
    private val collection = firestore.collection("genres")

    override suspend fun getAll(): List<Genre> = withContext(Dispatchers.IO) {
        collection.get().get().documents.mapNotNull { doc ->
            doc.toObject(Genre::class.java)?.copy(id = doc.id)
        }
    }

    override suspend fun getById(id: String): Genre? = withContext(Dispatchers.IO) {
        val doc = collection.document(id).get().get()
        if (doc.exists()) doc.toObject(Genre::class.java)?.copy(id = doc.id) else null
    }

    override suspend fun create(genre: Genre): Genre = withContext(Dispatchers.IO) {
        val docRef = collection.document()
        val genreWithId = genre.copy(id = docRef.id)
        docRef.set(genreWithId).get()
        genreWithId
    }

    override suspend fun update(genre: Genre): Boolean = withContext(Dispatchers.IO) {
        val id = genre.id ?: return@withContext false
        val docRef = collection.document(id)
        if (!docRef.get().get().exists()) return@withContext false
        docRef.set(genre).get()
        true
    }

    override suspend fun delete(id: String): Boolean = withContext(Dispatchers.IO) {
        collection.document(id).delete().get()
        true
    }
}
