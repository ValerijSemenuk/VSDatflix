package com.datflix.services

import com.datflix.models.Movie
import com.datflix.repositories.MovieRepository
import java.util.UUID

class MovieService(private val movieRepository: MovieRepository) {

    suspend fun getAllMovies(): List<Movie> = movieRepository.getAll()

    suspend fun getMovieById(id: String): Movie? = movieRepository.getById(id)

    suspend fun createMovie(movie: Movie): Movie {
        require(!movie.title.isNullOrBlank()) { "Movie title cannot be empty" }
        val id = movie.id ?: UUID.randomUUID().toString()
        require(movieRepository.getById(id) == null) { "Movie with this ID already exists" }
        val newMovie = movie.copy(id = id)
        return movieRepository.create(newMovie)
    }

    suspend fun updateMovie(movie: Movie): Boolean {
        val id = movie.id ?: return false
        val existing = movieRepository.getById(id) ?: return false
        return movieRepository.update(movie)
    }

    suspend fun deleteMovie(id: String): Boolean {
        val existing = movieRepository.getById(id) ?: return false
        return movieRepository.delete(id)
    }
}