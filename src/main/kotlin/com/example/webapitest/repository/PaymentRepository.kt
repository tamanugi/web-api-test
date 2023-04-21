package com.example.webapitest.repository

import com.example.webapitest.model.Payment
import com.example.webapitest.repository.db.*
import com.example.webapitest.repository.db.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class PaymentRepository {

    suspend fun sumAmount(fromDate: LocalDate, toDate: LocalDate): Int {
        return dbQuery {
            PaymentEntity.find {
                PaymentTable.transferDate.between(fromDate, toDate)
            }.sumOf { it.billingAmount}
        }
    }

    fun insert(payment: Payment): Payment {
        return transaction {
            val entity = PaymentEntity.new {
                userId = payment.userId
                amount = payment.amount
                fee = payment.fee
                feeRate = payment.feeRate
                taxRate = payment.taxRate
                billingAmount =payment.billingAmount
                transferDate = payment.transferDate
                uploadedDate = payment.uploadedDate
                status = payment.status
            }

            entity.toPayment()
        }
    }

}