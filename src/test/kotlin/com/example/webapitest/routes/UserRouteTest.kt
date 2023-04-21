package com.example.webapitest.routes

import io.ktor.server.testing.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*

import io.ktor.client.request.*

import io.ktor.http.*
import kotlin.test.Test
import kotlin.test.assertEquals

class UserRouteTest {
    @Test
    fun `firstTest`() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                gson()
            }
        }

        val res1 = client.get("/users")
        assertEquals(HttpStatusCode.OK, res1.status)

        val res2 = client.get("/users/1")
        assertEquals(HttpStatusCode.OK, res2.status)

        val res3 = client.post("/users")
        assertEquals(HttpStatusCode.OK, res3.status)
    }
}