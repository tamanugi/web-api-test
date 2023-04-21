package com.example.webapitest.repository.db

import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseFactory {
    fun init(config: ApplicationConfig): Database {
        val driverClassName = config.property("storage.db.driverClassName").getString()
        val jdbcURL = config.property("storage.db.jdbcURL").getString()
        val dbUser = config.property("storage.db.user").getString()
        val dbPassword = config.property("storage.db.password").getString()

        return Database.connect(createHikariDataSource(url = jdbcURL, driver = driverClassName, dbUser = dbUser, dbPassword = dbPassword))
    }

    private fun createHikariDataSource(
        url: String,
        driver: String,
        dbUser: String,
        dbPassword: String
    ) = HikariDataSource(HikariConfig().apply {
        driverClassName = driver
        jdbcUrl = url
        username = dbUser
        password = dbPassword
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    })

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}