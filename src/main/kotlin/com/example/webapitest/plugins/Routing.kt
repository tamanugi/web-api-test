package com.example.webapitest.plugins
import com.example.webapitest.routes.userRoutes
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.resources.*

fun Application.configureRouting() {
    install(Resources)
    routing {
        userRoutes()
    }
}
