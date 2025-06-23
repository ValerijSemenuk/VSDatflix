package com.datflix.routes

import com.datflix.firebase.FirebasePrincipal
import com.datflix.models.WatchlistItem
import com.datflix.services.WatchlistItemService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.watchlistRoutes(watchlistService: WatchlistItemService) {
    route("/watchlist") {
        get {
            call.respond(watchlistService.getAllWatchlistItems())
        }

        get("{id}") {
            val id = call.parameters["id"]
            if (id.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@get
            }
            val item = watchlistService.getWatchlistItemById(id)
            if (item == null) {
                call.respond(HttpStatusCode.NotFound, "Item not found")
            } else {
                call.respond(item)
            }
        }

        authenticate("firebase") {
            post {
                val principal = call.principal<FirebasePrincipal>() ?: return@post call.respond(HttpStatusCode.Unauthorized)
                val firebaseUid = principal.uid

                val body = call.receive<WatchlistItem>()
                val item = body.copy(userId = firebaseUid)
                val created = watchlistService.createWatchlistItem(item)
                call.respond(HttpStatusCode.Created, created)
            }

            put("{id}") {
                val id = call.parameters["id"]
                if (id.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                    return@put
                }

                val principal = call.principal<FirebasePrincipal>() ?: return@put call.respond(HttpStatusCode.Unauthorized)
                val firebaseUid = principal.uid

                val item = call.receive<WatchlistItem>()
                if (item.id != id) {
                    call.respond(HttpStatusCode.BadRequest, "ID mismatch")
                    return@put
                }

                if (item.userId != firebaseUid) {
                    call.respond(HttpStatusCode.Forbidden, "You are not the owner of this item")
                    return@put
                }

                val updated = watchlistService.updateWatchlistItem(item)
                if (updated) {
                    call.respond(HttpStatusCode.OK, "Item updated")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Item not found")
                }
            }

            delete("{id}") {
                val id = call.parameters["id"]
                if (id.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                    return@delete
                }

                val principal = call.principal<FirebasePrincipal>() ?: return@delete call.respond(HttpStatusCode.Unauthorized)
                val firebaseUid = principal.uid

                val existing = watchlistService.getWatchlistItemById(id)
                if (existing == null) {
                    call.respond(HttpStatusCode.NotFound, "Item not found")
                    return@delete
                }

                if (existing.userId != firebaseUid) {
                    call.respond(HttpStatusCode.Forbidden, "You are not the owner of this item")
                    return@delete
                }

                val deleted = watchlistService.deleteWatchlistItem(id)
                if (deleted) {
                    call.respond(HttpStatusCode.OK, "Item deleted")
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to delete")
                }
            }
        }
    }
}
