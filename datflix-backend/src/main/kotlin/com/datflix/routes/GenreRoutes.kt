package com.datflix.routes

import com.datflix.models.Genre
import com.datflix.services.GenreService
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

fun Route.genreRoutes(genreService: GenreService) {
    route("/genres") {
        get {
            call.respond(genreService.getAllGenres())
        }
        get("{id}") {
            val id = call.parameters["id"]
            if (id.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@get
            }
            val genre = genreService.getGenreById(id)
            if (genre == null) {
                call.respond(HttpStatusCode.NotFound, "Genre not found")
            } else {
                call.respond(genre)
            }
        }
        post {
            val genre = call.receive<Genre>()
            try {
                val created = genreService.createGenre(genre)
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
            val genre = call.receive<Genre>()
            if (genre.id != id) {
                call.respond(HttpStatusCode.BadRequest, "ID in path and body do not match")
                return@put
            }
            val updated = genreService.updateGenre(genre)
            if (updated) {
                call.respond(HttpStatusCode.OK, "Genre updated")
            } else {
                call.respond(HttpStatusCode.NotFound, "Genre not found")
            }
        }
        delete("{id}") {
            val id = call.parameters["id"]
            if (id.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@delete
            }
            val deleted = genreService.deleteGenre(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Genre deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "Genre not found")
            }
        }
    }
}
