package com.datflix.routes

import com.datflix.models.Actor
import com.datflix.services.ActorService
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

fun Route.actorRoutes(actorService: ActorService) {
    route("/actors") {
        get {
            call.respond(actorService.getAllActors())
        }
        get("{id}") {
            val id = call.parameters["id"]
            if (id.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@get
            }
            val actor = actorService.getActorById(id)
            if (actor == null) {
                call.respond(HttpStatusCode.NotFound, "Actor not found")
            } else {
                call.respond(actor)
            }
        }
        post {
            val actor = call.receive<Actor>()
            try {
                val created = actorService.createActor(actor)
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
            val actor = call.receive<Actor>()
            if (actor.id != id) {
                call.respond(HttpStatusCode.BadRequest, "ID in path and body do not match")
                return@put
            }
            val updated = actorService.updateActor(actor)
            if (updated) {
                call.respond(HttpStatusCode.OK, "Actor updated")
            } else {
                call.respond(HttpStatusCode.NotFound, "Actor not found")
            }
        }
        delete("{id}") {
            val id = call.parameters["id"]
            if (id.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@delete
            }
            val deleted = actorService.deleteActor(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "Actor deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "Actor not found")
            }
        }
    }
}
