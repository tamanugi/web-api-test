package com.example.webapitest.service

import com.example.webapitest.model.Payment
import com.example.webapitest.model.PaymentBankAccounts
import com.example.webapitest.repository.PaymentBankAccountsRepository
import com.example.webapitest.repository.PaymentRepository
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.IllegalStateException
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PaymentService(private val paymentRepository: PaymentRepository, private val paymentBankAccountsRepository: PaymentBankAccountsRepository) {
    suspend fun amount(from: String, to: String) : Int {
        val fromDate = parseStringDate(from)
        val toDate = parseStringDate(to)

        return paymentRepository.sumAmount(fromDate, toDate)
    }

    private fun parseStringDate(value: String): LocalDate {
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(value, dateFormat)
    }

    fun register(amount: Int) : Payment {
        val feeRate = 0.04
        val taxRate = 0.1
        // 端数切捨て？
        val fee = (amount * feeRate).toInt()
        val tax = (amount * taxRate).toInt()

        val billingAmount = amount + fee + tax

        return transaction {
            val p = Payment(
                id = null,
                userId = 1,
                amount = amount,
                fee = fee,
                feeRate = BigDecimal.valueOf(feeRate),
                // FIXME: DB側の定義が間違っていそう
                taxRate = taxRate.toInt(),
                billingAmount = billingAmount,

                // TODO: TZで事故りそうなので考慮したい
                transferDate = LocalDate.now(),
                // TODO: TZで事故りそうなので考慮したい
                uploadedDate = LocalDate.now(),
                status = 0,
            )

            val payment =  paymentRepository.insert(payment = p)

            paymentBankAccountsRepository.insert(
                PaymentBankAccounts(
                    payment_id = payment.id ?: throw IllegalStateException(),
                    bank_code = "",
                    bank_name = "",
                    branch_code = "",
                    branch_name = "",
                    account_type = 0,
                    acount_number = "",
                    acount_holder = "",
                ),
            )

            payment
        }

    }


}