package com.example.webapitest.plugins

import com.example.webapitest.repository.db.DatabaseFactory
import io.ktor.server.config.*
import io.ktor.server.application.*

fun Application.configureDatabase() {
    DatabaseFactory.init(environment.config)
}