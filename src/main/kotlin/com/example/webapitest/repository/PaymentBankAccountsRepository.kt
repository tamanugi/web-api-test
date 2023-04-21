package com.example.webapitest.repository

import com.example.webapitest.model.Payment
import com.example.webapitest.model.PaymentBankAccounts
import com.example.webapitest.repository.db.*
import org.jetbrains.exposed.sql.transactions.transaction

class PaymentBankAccountsRepository {

    fun insert(paymentBankAccounts: PaymentBankAccounts): PaymentBankAccounts {

        return transaction {
            val entity = PaymentBankAccountsEntity.new {
                payment_id     = paymentBankAccounts.payment_id
                bank_code      = paymentBankAccounts.bank_code
                bank_name      = paymentBankAccounts.bank_name
                branch_code    = paymentBankAccounts.branch_code
                branch_name    = paymentBankAccounts.branch_name
                account_type   = paymentBankAccounts.account_type
                account_number = paymentBankAccounts.acount_number
                account_holder = paymentBankAccounts.acount_holder
            }

            entity.toPaymentBankAccounts()
        }
    }
}