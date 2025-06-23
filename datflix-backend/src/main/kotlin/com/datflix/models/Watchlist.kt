package com.datflix.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class WatchlistItem(
    var id: String? = null,
    var userId: String? = null,
    var movieId: String? = null,
    @Contextual
    var addedAt: LocalDateTime? = null,
    var isWatched: Boolean = false,
    var userRating: Int? = null
) {
    fun isRecentlyAdded(days: Int = 7): Boolean {
        return addedAt?.isAfter(LocalDateTime.now().minusDays(days.toLong())) ?: false
    }
}
