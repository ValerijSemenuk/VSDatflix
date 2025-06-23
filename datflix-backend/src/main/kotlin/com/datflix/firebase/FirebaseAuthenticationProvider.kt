package com.datflix.firebase

import com.google.firebase.auth.FirebaseAuth
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.AuthenticationContext
import io.ktor.server.auth.AuthenticationFailedCause
import io.ktor.server.auth.AuthenticationProvider
import io.ktor.server.response.respond

class FirebaseAuthenticationProvider internal constructor(
    config: Config
) : AuthenticationProvider(config) {

    class Config(name: String?) : AuthenticationProvider.Config(name)

    override suspend fun onAuthenticate(context: AuthenticationContext) {
        val call = context.call
        val authHeader = call.request.headers["Authorization"]

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            context.challenge("FirebaseAuth", AuthenticationFailedCause.NoCredentials) { challenge, call ->
                call.respond(HttpStatusCode.Unauthorized, "Missing or invalid Authorization header")
                challenge.complete()
            }
            return
        }

        val token = authHeader.removePrefix("Bearer ").trim()

        try {
            val decodedToken = FirebaseAuth.getInstance().verifyIdToken(token)
            val principal = FirebasePrincipal(decodedToken.uid)
            context.principal(principal)
        } catch (e: Exception) {
            context.challenge("FirebaseAuth", AuthenticationFailedCause.InvalidCredentials) { challenge, call ->
                call.respond(HttpStatusCode.Unauthorized, "Invalid Firebase token: ${e.message}")
                challenge.complete()
            }
        }
    }
}

fun io.ktor.server.auth.AuthenticationConfig.firebase(
    name: String? = null,
    configure: FirebaseAuthenticationProvider.Config.() -> Unit = {}
) {
    val provider = FirebaseAuthenticationProvider(FirebaseAuthenticationProvider.Config(name).apply(configure))
    register(provider)
}