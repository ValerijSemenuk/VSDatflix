package com.datflix.firebase

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication

fun Application.configureAuth() {
    install(Authentication) {
        firebase("firebase") {
        }
    }
}