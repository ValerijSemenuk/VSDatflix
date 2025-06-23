package com.datflix.repositories

import com.datflix.models.Actor
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActorRepository : Repository<Actor, String> {

    private val firestore: Firestore = FirestoreClient.getFirestore()
    private val collection = firestore.collection("actors")

    override suspend fun getAll(): List<Actor> = withContext(Dispatchers.IO) {
        collection.get().get().documents.mapNotNull { doc ->
            doc.toObject(Actor::class.java)?.copy(id = doc.id)
        }
    }

    override suspend fun getById(id: String): Actor? = withContext(Dispatchers.IO) {
        val doc = collection.document(id).get().get()
        if (doc.exists()) doc.toObject(Actor::class.java)?.copy(id = doc.id) else null
    }

    override suspend fun create(actor: Actor): Actor = withContext(Dispatchers.IO) {
        val docRef = collection.document()
        val actorWithId = actor.copy(id = docRef.id)
        docRef.set(actorWithId).get()
        actorWithId
    }

    override suspend fun update(actor: Actor): Boolean = withContext(Dispatchers.IO) {
        val id = actor.id ?: return@withContext false
        val docRef = collection.document(id)
        if (!docRef.get().get().exists()) return@withContext false
        docRef.set(actor).get()
        true
    }

    override suspend fun delete(id: String): Boolean = withContext(Dispatchers.IO) {
        collection.document(id).delete().get()
        true
    }
}
