package com.datflix.models
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    var success: Boolean = false,
    var message: String? = null,
    var data: T? = null
)
