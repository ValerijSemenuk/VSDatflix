package com.datflix.repositories

import com.datflix.models.User
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository : Repository<User, String> {

    private val firestore: Firestore = FirestoreClient.getFirestore()
    private val collection = firestore.collection("users")

    override suspend fun getAll(): List<User> = withContext(Dispatchers.IO) {
        collection.get().get().documents.mapNotNull { it.toObject(User::class.java) }
    }

    override suspend fun getById(id: String): User? = withContext(Dispatchers.IO) {
        val doc = collection.document(id).get().get()
        if (doc.exists()) doc.toObject(User::class.java) else null
    }

    override suspend fun create(user: User): User = withContext(Dispatchers.IO) {
        val newDocRef = collection.document()
        val userWithId = user.copy(id = newDocRef.id)
        newDocRef.set(userWithId).get()
        userWithId
    }

    override suspend fun update(user: User): Boolean = withContext(Dispatchers.IO) {
        val id = user.id ?: return@withContext false
        val docRef = collection.document(id)
        if (!docRef.get().get().exists()) return@withContext false
        docRef.set(user).get()
        true
    }

    override suspend fun delete(id: String): Boolean = withContext(Dispatchers.IO) {
        collection.document(id).delete().get()
        true
    }
}
