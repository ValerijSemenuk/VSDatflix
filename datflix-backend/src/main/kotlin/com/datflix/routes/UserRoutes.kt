
import com.datflix.firebase.FirebasePrincipal
import com.datflix.models.User
import com.datflix.services.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.userRoutes(userService: UserService) {
    route("/users") {
        get {
            val principal = call.principal<FirebasePrincipal>()
            if (principal == null) {
                call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
                return@get
            }
            call.respond(userService.getAllUsers())
        }
        get("{id}") {
            val principal = call.principal<FirebasePrincipal>()
            if (principal == null) {
                call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
                return@get
            }
            val id = call.parameters["id"]
            if (id.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@get
            }
            val user = userService.getUserById(id)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "User not found")
            } else {
                call.respond(user)
            }
        }
        post {
            val principal = call.principal<FirebasePrincipal>()
            if (principal == null) {
                call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
                return@post
            }
            val user = call.receive<User>()
            try {
                val created = userService.createUser(user)
                call.respond(HttpStatusCode.Created, created)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Invalid data")
            }
        }
        put("{id}") {
            val principal = call.principal<FirebasePrincipal>()
            if (principal == null) {
                call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
                return@put
            }
            val id = call.parameters["id"]
            if (id.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@put
            }
            val user = call.receive<User>()
            if (user.id != id) {
                call.respond(HttpStatusCode.BadRequest, "ID in path and body do not match")
                return@put
            }
            val updated = userService.updateUser(user)
            if (updated) {
                call.respond(HttpStatusCode.OK, "User updated")
            } else {
                call.respond(HttpStatusCode.NotFound, "User not found")
            }
        }
        delete("{id}") {
            val principal = call.principal<FirebasePrincipal>()
            if (principal == null) {
                call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
                return@delete
            }
            val id = call.parameters["id"]
            if (id.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing id")
                return@delete
            }
            val deleted = userService.deleteUser(id)
            if (deleted) {
                call.respond(HttpStatusCode.OK, "User deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "User not found")
            }
        }
    }
}
