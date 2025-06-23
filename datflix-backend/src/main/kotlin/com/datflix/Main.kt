package com.datflix
import com.datflix.config.configureRouting
import com.datflix.firebase.configureAuth
import com.datflix.firebase.configureFirebase
import com.datflix.repositories.ActorRepository
import com.datflix.repositories.CastRepository
import com.datflix.repositories.GenreRepository
import com.datflix.repositories.MovieRepository
import com.datflix.repositories.ReviewRepository
import com.datflix.repositories.UserRepository
import com.datflix.repositories.WatchlistItemRepository
import com.datflix.services.ActorService
import com.datflix.services.CastService
import com.datflix.services.GenreService
import com.datflix.services.MovieService
import com.datflix.services.ReviewService
import com.datflix.services.UserService
import com.datflix.services.WatchlistItemService
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json


fun main() {
    embeddedServer(
        factory = Netty,
        port = 8000,
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configureFirebase()
    configureAuth()

    // --- Підключаємо JSON серіалізацію ---
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    val actorService = ActorService(ActorRepository())
    val castService = CastService(CastRepository())
    val genreService = GenreService(GenreRepository())
    val movieService = MovieService(MovieRepository())
    val reviewService = ReviewService(ReviewRepository())
    val userService = UserService(UserRepository())
    val watchlistItemService = WatchlistItemService(WatchlistItemRepository())

    configureRouting(
        actorService,
        castService,
        genreService,
        movieService,
        reviewService,
        userService,
        watchlistItemService
    )
}