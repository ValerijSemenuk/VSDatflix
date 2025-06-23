package com.datflix.routes

import com.datflix.models.Cast
import com.datflix.services.CastService
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

fun Route.castRoutes(castService: CastService) {
    route("/casts") {
        get {
            call.respond(castService.getAllCasts())
        }
        get("{id}") {
            val id = call.parameters["id"]
            if (id.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@get
            }
            val cast = castService.getCastById(id)
            if (cast == null) {
                call.respond(HttpStatusCode.NotFound, "Cast not found")
            } else {
                call.respond(cast)
            }
        }
        post {
            val cast = call.receive<Cast>()
            try {
                val created = castService.createCast(cast)
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
            val cast = call.receive<Cast>()
            if (cast.id != id) {
                call.respond(HttpStatusCode.BadRequest, "ID in path and body do not match")
                return@put
            }
            val updated = castService.updateCast(cast)
            if (updated) {
                call.respond(HttpStatusCode.OK, "Cast updated")
            } else {
                call.respond(HttpStatusCode.NotFound, "Cast not found")
            }
        }
        delete("{id}") {
            val id = call.parameters["id"]
            if (id.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@delete
            }
            val deleted = castService.deleteCast(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Cast deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "Cast not found")
            }
        }
    }
}
