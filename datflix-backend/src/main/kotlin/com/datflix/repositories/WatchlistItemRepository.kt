package com.datflix.repositories

import com.datflix.models.WatchlistItem
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WatchlistItemRepository : Repository<WatchlistItem, String> {

    private val firestore: Firestore = FirestoreClient.getFirestore()
    private val collection = firestore.collection("watchlist_items")

    override suspend fun getAll(): List<WatchlistItem> = withContext(Dispatchers.IO) {
        collection.get().get().documents.mapNotNull { it.toObject(WatchlistItem::class.java) }
    }

    override suspend fun getById(id: String): WatchlistItem? = withContext(Dispatchers.IO) {
        val doc = collection.document(id).get().get()
        if (doc.exists()) doc.toObject(WatchlistItem::class.java) else null
    }

    override suspend fun create(item: WatchlistItem): WatchlistItem = withContext(Dispatchers.IO) {
        val newDocRef = collection.document()
        val itemWithId = item.copy(id = newDocRef.id)
        newDocRef.set(itemWithId).get()
        itemWithId
    }

    override suspend fun update(item: WatchlistItem): Boolean = withContext(Dispatchers.IO) {
        val id = item.id ?: return@withContext false
        val docRef = collection.document(id)
        if (!docRef.get().get().exists()) return@withContext false
        docRef.set(item).get()
        true
    }

    override suspend fun delete(id: String): Boolean = withContext(Dispatchers.IO) {
        collection.document(id).delete().get()
        true
    }
}
