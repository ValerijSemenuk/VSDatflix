package com.datflix.models
import kotlinx.serialization.Serializable

@Serializable
data class Actor(
    var id: String? = null,
    var name: String? = null,
    var photoUrl: String? = null,
    var bio: String? = null,
    var movies: List<String> = emptyList()
)
