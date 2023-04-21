package com.example.webapitest.routes

import io.ktor.resources.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

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
fun Route.userRoutes() {
    get<UserResource> {
        call.respondText("implement me")
    }

    get<UserResource.Id> {
        call.respondText("implement me ${it.id}")
    }

    post<UserResource.New> {
        call.respondText("implement me")
    }
}
