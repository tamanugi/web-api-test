package com.example.webapitest.plugins
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import java.util.HashMap

fun Application.configureRouting() {
    routing {
        get("/hello") {
            call.respond("test" to "hogehoge")
        }
    }
}
