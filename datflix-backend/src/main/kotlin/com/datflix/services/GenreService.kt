package com.datflix.services
import com.datflix.models.Genre
import com.datflix.repositories.GenreRepository
import java.util.UUID

class GenreService(private val genreRepository: GenreRepository) {

    suspend fun getAllGenres(): List<Genre> = genreRepository.getAll()

    suspend fun getGenreById(id: String): Genre? = genreRepository.getById(id)

    suspend fun createGenre(genre: Genre): Genre {
        require(!genre.name.isNullOrBlank()) { "Genre name cannot be empty" }
        val id = genre.id ?: UUID.randomUUID().toString()
        require(genreRepository.getById(id) == null) { "Genre with this ID already exists" }
        val newGenre = genre.copy(id = id)
        return genreRepository.create(newGenre)
    }

    suspend fun updateGenre(genre: Genre): Boolean {
        val id = genre.id ?: return false
        val existing = genreRepository.getById(id) ?: return false
        return genreRepository.update(genre)
    }

    suspend fun deleteGenre(id: String): Boolean {
        val existing = genreRepository.getById(id) ?: return false
        return genreRepository.delete(id)
    }
}
