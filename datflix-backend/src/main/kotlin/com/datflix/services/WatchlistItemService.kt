package com.datflix.services
import com.datflix.models.WatchlistItem
import com.datflix.repositories.WatchlistItemRepository
import java.util.UUID

class WatchlistItemService(private val watchlistRepository: WatchlistItemRepository) {

    suspend fun getAllWatchlistItems(): List<WatchlistItem> = watchlistRepository.getAll()

    suspend fun getWatchlistItemById(id: String): WatchlistItem? = watchlistRepository.getById(id)

    suspend fun createWatchlistItem(item: WatchlistItem): WatchlistItem {
        val id = item.id ?: UUID.randomUUID().toString()
        val newItem = item.copy(id = id)
        return watchlistRepository.create(newItem)
    }

    suspend fun updateWatchlistItem(item: WatchlistItem): Boolean {
        val id = item.id ?: return false
        val existing = watchlistRepository.getById(id) ?: return false
        return watchlistRepository.update(item)
    }

    suspend fun deleteWatchlistItem(id: String): Boolean {
        val existing = watchlistRepository.getById(id) ?: return false
        return watchlistRepository.delete(id)
    }
}
