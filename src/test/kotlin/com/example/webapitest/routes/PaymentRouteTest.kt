package com.example.webapitest.routes
import io.ktor.client.request.*

import com.example.webapitest.model.Payment
import com.example.webapitest.repository.PaymentRepository
import com.example.webapitest.repository.db.DatabaseFactory
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

import io.ktor.client.call.*

class PaymentRouteTest {

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
    fun `合計金額取得API_期間を指定して合計金額を取得できること`() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                gson()
            }
        }

        transaction {
            // 2023/2/10 1,000円
            PaymentRepository().insert(dummyPayment(1000, LocalDate.of(2023, 2, 10)))

            // 2023/2/11 2,000円
            PaymentRepository().insert(dummyPayment(2000, LocalDate.of(2023, 2, 11)))

            // 2023/2/12 3,000円
            PaymentRepository().insert(dummyPayment(3000, LocalDate.of(2023, 2, 12)))

            // 2023/2/13 4,000円
            PaymentRepository().insert(dummyPayment(4000, LocalDate.of(2023, 2, 13)))

            // 2023/2/14 5,000円
            PaymentRepository().insert(dummyPayment(5000, LocalDate.of(2023, 2, 14)))
        }

        val res1: AmountResponse = client.get("/payments/amount?from=2023-02-12&to=2023-02-12").body()
        assertEquals(3000, res1.amount)

        val res2: AmountResponse = client.get("/payments/amount?from=2023-02-11&to=2023-02-13").body()
        assertEquals(2000 + 3000 + 4000, res2.amount)

        val res3: AmountResponse = client.get("/payments/amount?from=2023-02-10&to=2023-02-14").body()
        assertEquals(1000 + 2000 + 3000 + 4000 + 5000, res3.amount)
    }

    private fun dummyPayment(billingAmount: Int, transferDate: LocalDate): Payment {
        return Payment(
            id = null,
            userId = 1,
            amount = 100,
            fee = 0,
            feeRate = BigDecimal.valueOf(0.00005),
            taxRate = 0,
            billingAmount = billingAmount,
            transferDate = transferDate,
            uploadedDate = LocalDate.now(),
            status = 0,
        )
    }
}