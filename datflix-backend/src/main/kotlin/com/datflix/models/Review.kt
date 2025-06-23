package com.datflix.models
import kotlinx.serialization.Serializable

@Serializable
data class Review(
    var id: String? = null,
    var userId: String? = null,
    var movieId: String? = null,
    var text: String? = null,
    var rating: Int? = null
)
