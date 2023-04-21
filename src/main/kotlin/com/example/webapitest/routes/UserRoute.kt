package com.example.webapitest.routes

import com.example.webapitest.model.User
import com.example.webapitest.service.UserService
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*

// -------
// resource
// -------

@Resource("/users")
class UserResource(val page: Int = 1, val limit: Int = 10) {
    @Resource("{id}")
    class Id(val parent: UserResource = UserResource(), val id: Long)

    @Resource("")
    class New(val parent: UserResource = UserResource())
}

// -------
// routing
// -------
fun Route.userRoutes(
    // TODO: DI by koin
    userService: UserService) {

    get<UserResource> {
        call.respondText("implement me")
    }

    get<UserResource.Id> {
        val user = userService.findUserById(it.id)
        call.respond(user ?: HttpStatusCode.NotFound)
    }

    post<UserResource.New> {
        val user = call.receive<User>()
        val createdUser = userService.createUser(user)
        call.response.status(HttpStatusCode.Created)
        call.respond(mapOf("id" to createdUser.id))
    }
}
