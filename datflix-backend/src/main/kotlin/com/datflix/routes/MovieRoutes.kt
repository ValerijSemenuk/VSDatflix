package com.datflix.routes

import com.datflix.models.Movie
import com.datflix.services.MovieService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.movieRoutes(movieService: MovieService) {
    route("/movies") {
        get {
            call.respond(movieService.getAllMovies())
        }
        get("{id}") {
            val id = call.parameters["id"]
            if (id.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@get
            }
            val movie = movieService.getMovieById(id)
            if (movie == null) {
                call.respond(HttpStatusCode.NotFound, "Movie not found")
            } else {
                call.respond(movie)
            }
        }
        post {
            val movie = call.receive<Movie>()
            try {
                val created = movieService.createMovie(movie)
                call.respond(HttpStatusCode.Created, created)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Invalid data")
            }
        }
        put("{id}") {
            val id = call.parameters["id"]
            if (id.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@put
            }
            val movie = call.receive<Movie>()
            if (movie.id != id) {
                call.respond(HttpStatusCode.BadRequest, "ID in path and body do not match")
                return@put
            }
            val updated = movieService.updateMovie(movie)
            if (updated) {
                call.respond(HttpStatusCode.OK, "Movie updated")
            } else {
                call.respond(HttpStatusCode.NotFound, "Movie not found")
            }
        }
        delete("{id}") {
            val id = call.parameters["id"]
            if (id.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@delete
            }
            val deleted = movieService.deleteMovie(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Movie deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "Movie not found")
            }
        }
    }
}
