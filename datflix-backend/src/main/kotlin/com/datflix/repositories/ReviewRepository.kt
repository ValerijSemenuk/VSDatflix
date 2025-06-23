package com.datflix.repositories

import com.datflix.models.Review
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewRepository : Repository<Review, String> {

    private val firestore: Firestore = FirestoreClient.getFirestore()
    private val collection = firestore.collection("reviews")

    override suspend fun getAll(): List<Review> = withContext(Dispatchers.IO) {
        collection.get().get().documents.mapNotNull { it.toObject(Review::class.java) }
    }

    override suspend fun getById(id: String): Review? = withContext(Dispatchers.IO) {
        val doc = collection.document(id).get().get()
        if (doc.exists()) doc.toObject(Review::class.java) else null
    }

    override suspend fun create(review: Review): Review = withContext(Dispatchers.IO) {
        val docRef = collection.document()
        val reviewWithId = review.copy(id = docRef.id)
        docRef.set(reviewWithId).get()
        reviewWithId
    }

    override suspend fun update(review: Review): Boolean = withContext(Dispatchers.IO) {
        val id = review.id ?: return@withContext false
        val docRef = collection.document(id)
        if (!docRef.get().get().exists()) return@withContext false
        docRef.set(review).get()
        true
    }

    override suspend fun delete(id: String): Boolean = withContext(Dispatchers.IO) {
        collection.document(id).delete().get()
        true
    }
}

