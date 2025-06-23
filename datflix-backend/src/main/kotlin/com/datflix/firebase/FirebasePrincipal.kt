package com.datflix.firebase

import io.ktor.server.auth.Principal

data class FirebasePrincipal(val uid: String) : Principal