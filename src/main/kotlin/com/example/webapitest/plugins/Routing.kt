package com.example.webapitest.plugins
import com.example.webapitest.repository.PaymentRepository
import com.example.webapitest.repository.UserRepository
import com.example.webapitest.routes.paymentRoute
import com.example.webapitest.routes.userRoutes
import com.example.webapitest.service.PaymentService
import com.example.webapitest.service.UserService
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.resources.*

fun Application.configureRouting() {
    install(Resources)
    routing {
        // TODO: DI by koin
        userRoutes(UserService(UserRepository()))

        // TODO: DI by koin
        paymentRoute(PaymentService(PaymentRepository()))
    }
}
