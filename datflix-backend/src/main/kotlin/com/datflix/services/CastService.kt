package com.datflix.services
import com.datflix.models.Cast
import com.datflix.repositories.CastRepository
import java.util.UUID

class CastService(private val castRepository: CastRepository) {

    suspend fun getAllCasts(): List<Cast> = castRepository.getAll()

    suspend fun getCastById(id: String): Cast? = castRepository.getById(id)

    suspend fun createCast(cast: Cast): Cast {
        require(!cast.characterName.isNullOrBlank()) { "Character name cannot be empty" }
        val id = cast.id ?: UUID.randomUUID().toString()
        require(castRepository.getById(id) == null) { "Cast with this ID already exists" }
        val newCast = cast.copy(id = id)
        return castRepository.create(newCast)
    }

    suspend fun updateCast(cast: Cast): Boolean {
        val id = cast.id ?: return false
        val existing = castRepository.getById(id) ?: return false
        return castRepository.update(cast)
    }

    suspend fun deleteCast(id: String): Boolean {
        val existing = castRepository.getById(id) ?: return false
        return castRepository.delete(id)
    }
}
