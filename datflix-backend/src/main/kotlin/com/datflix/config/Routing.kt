package com.datflix.config

import com.datflix.routes.actorRoutes
import com.datflix.routes.castRoutes
import com.datflix.routes.genreRoutes
import com.datflix.routes.movieRoutes
import com.datflix.routes.reviewRoutes
import com.datflix.routes.watchlistRoutes
import com.datflix.services.ActorService
import com.datflix.services.CastService
import com.datflix.services.GenreService
import com.datflix.services.MovieService
import com.datflix.services.ReviewService
import com.datflix.services.UserService
import com.datflix.services.WatchlistItemService
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.routing
import userRoutes

fun Application.configureRouting(
        actorService: ActorService,
        castService: CastService,
        genreService: GenreService,
        movieService: MovieService,
        reviewService: ReviewService,
        userService: UserService,
        watchlistItemService: WatchlistItemService
) {
        routing {
                actorRoutes(actorService)
                castRoutes(castService)
                genreRoutes(genreService)
                movieRoutes(movieService)

                authenticate("firebase") {
                        reviewRoutes(reviewService)
                        userRoutes(userService)
                        watchlistRoutes(watchlistItemService)
                }
        }
}
