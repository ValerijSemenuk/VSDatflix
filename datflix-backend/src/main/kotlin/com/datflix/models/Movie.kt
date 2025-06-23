package com.datflix.models

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    var id: String? = null,
    var title: String? = null,
    var description: String? = null,
    var releaseYear: Int? = null,
    var durationMinutes: Int? = null,
    var posterUrl: String? = null,
    var trailerUrl: String? = null,
    var videoUrl: String? = null,
    var genreIds: List<String>? = null,
    var averageRating: Float = 0f,
    var isTrending: Boolean = false
)
