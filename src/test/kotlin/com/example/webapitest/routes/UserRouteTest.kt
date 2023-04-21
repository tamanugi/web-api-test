package com.example.webapitest.routes

import com.example.webapitest.model.User
import com.example.webapitest.repository.UserRepository
import com.example.webapitest.repository.db.DatabaseFactory
import io.ktor.client.call.*
import io.ktor.server.testing.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*

import io.ktor.client.request.*

import io.ktor.http.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserRouteTest {

    @Before
    fun setup() {
        //  TODO: to Rule
        //  cleanup db
        //  ref: https://qiita.com/tumugin/items/1afa9fea7fafabf81a9d
        DatabaseFactory.init(ApplicationConfig("application.conf"))
        transaction {
            // 外部キー制約があるテーブルをTRUNCATEするために一時的に制約を取る
            TransactionManager.current().exec("SET FOREIGN_KEY_CHECKS = 0")
            // flywayのテーブルを除いて全てのテーブルをTRUNCATEする
            db.dialect.allTablesNames().filterNot { it.contains("flyway_schema_history") }.forEach {
                TransactionManager.current().exec("TRUNCATE TABLE $it")
            }
            // 一時的に外していた制約を戻す
            TransactionManager.current().exec("SET FOREIGN_KEY_CHECKS = 1")
        }
    }

    @Test
    fun `ユーザー取得_ID指定してユーザーが取得できること`() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                gson()
            }
        }

        var userId: Long?  = null
        transaction {
            val user =
            UserRepository().insert(User(
                id = null,
                organizationId = 1L,
                name = "ほげ山ほげ太郎",
                email = "hoge@example.com",
                password = "password",
                verified = true
            ))

            userId = user.id
        }

        val user: User = client.get("/users/${userId}").body()
        assertEquals("ほげ山ほげ太郎", user.name)
        assertEquals("hoge@example.com", user.email)
        assertEquals(1, user.organizationId)
        // FIXME: パスワードを返したらあっかーん！
         assertEquals("password", user.password)
        assertTrue(user.verified)
    }

    @Test
    fun `ユーザー取得_存在しないIDの場合はNotFound`() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                gson()
            }
        }

        val res1 = client.get("/users/9999")
        assertEquals(HttpStatusCode.NotFound, res1.status)
    }
}