package com.datflix.models

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: String? = null,
    val name: String? = null,
    val iconUrl: String? = null
)