package com.example.webapitest

import com.example.webapitest.plugins.configureRouting
import com.example.webapitest.plugins.configureSerialization
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    connectDB()

    configureSerialization()
    configureRouting()
}

private fun connectDB() {
    val hikariConfig = HikariConfig()
    hikariConfig.driverClassName = "com.mysql.cj.jdbc.Driver"
    hikariConfig.jdbcUrl = "jdbc:mysql://localhost:3316/shiharai?allowPublicKeyRetrieval=true&useSSL=false"
    hikariConfig.username = "testuser"
    hikariConfig.password = "password"
    hikariConfig.maximumPoolSize = 3
    hikariConfig.isAutoCommit = false
    hikariConfig.transactionIsolation = "TRANSACTION_REPEATABLE_READ"

    val dataSource = HikariDataSource(hikariConfig)
    Database.connect(dataSource)
}