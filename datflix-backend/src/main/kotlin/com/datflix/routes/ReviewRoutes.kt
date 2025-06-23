package com.datflix.routes

import com.datflix.firebase.FirebasePrincipal
import com.datflix.models.Review
import com.datflix.services.ReviewService
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

fun Route.reviewRoutes(reviewService: ReviewService) {
    authenticate("firebase") {
        route("/reviews") {

            get {
                call.respond(reviewService.getAllReviews())
            }

            get("{id}") {
                val id = call.parameters["id"]
                if (id == null) return@get call.respond(HttpStatusCode.BadRequest, "Missing id")
                val review = reviewService.getReviewById(id)
                if (review == null) {
                    call.respond(HttpStatusCode.NotFound, "Review not found")
                } else {
                    call.respond(review)
                }
            }

            post {
                val principal = call.principal<FirebasePrincipal>() ?: return@post call.respond(HttpStatusCode.Unauthorized)
                val firebaseUid = principal.uid

                val body = call.receive<Review>()
                val review = body.copy(userId = firebaseUid)
                val created = reviewService.createReview(review)
                call.respond(HttpStatusCode.Created, created)
            }

            put("{id}") {
                val id = call.parameters["id"]
                if (id == null) return@put call.respond(HttpStatusCode.BadRequest, "Missing id")

                val principal = call.principal<FirebasePrincipal>() ?: return@put call.respond(HttpStatusCode.Unauthorized)
                val firebaseUid = principal.uid

                val review = call.receive<Review>()
                if (review.id != id) return@put call.respond(HttpStatusCode.BadRequest, "ID mismatch")

                if (review.userId != firebaseUid) {
                    return@put call.respond(HttpStatusCode.Forbidden, "You are not the owner of this review")
                }

                val updated = reviewService.updateReview(review)
                if (updated) {
                    call.respond(HttpStatusCode.OK, "Review updated")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Review not found")
                }
            }

            delete("{id}") {
                val id = call.parameters["id"]
                if (id == null) return@delete call.respond(HttpStatusCode.BadRequest, "Missing id")

                val principal = call.principal<FirebasePrincipal>() ?: return@delete call.respond(HttpStatusCode.Unauthorized)
                val firebaseUid = principal.uid

                val existing = reviewService.getReviewById(id)
                if (existing == null) return@delete call.respond(HttpStatusCode.NotFound, "Review not found")

                if (existing.userId != firebaseUid) {
                    return@delete call.respond(HttpStatusCode.Forbidden, "You are not the owner of this review")
                }

                val deleted = reviewService.deleteReview(id)
                if (deleted) {
                    call.respond(HttpStatusCode.OK, "Review deleted")
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to delete")
                }
            }
        }
    }
}
