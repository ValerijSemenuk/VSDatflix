package com.datflix.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class User(
    var id: String? = null,
    var email: String? = null,
    var username: String? = null,
    var passwordHash: String? = null,
    var avatarUrl: String? = null,
    var watchlist: List<String> = emptyList(),
    @Contextual
    var registrationDate: LocalDateTime? = null
)