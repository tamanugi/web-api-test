package com.example.webapitest.repository.db

import com.example.webapitest.model.PaymentBankAccounts
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

object PaymentBankAccountsTable : LongIdTableBase("payment_bank_accounts") {
    val payment_id = long("payment_id")
    val bank_code = varchar("bank_code", length  = 4)
    val bank_name = varchar("bank_name", length = 64)
    val branch_code = varchar("branch_code", length = 3)
    val branch_name = varchar("branch_name", length = 64)
    val account_type = integer("account_type")
    val account_number = varchar("account_number", length = 8)
    val account_holder = varchar("account_holder", length = 128 )
}

class PaymentBankAccountsEntity(id: EntityID<Long>) : LongEntityBase(id) {
    companion object : LongEntityClass<PaymentBankAccountsEntity>(PaymentBankAccountsTable)

    var payment_id  by PaymentBankAccountsTable.payment_id
    var bank_code  by PaymentBankAccountsTable.bank_code
    var bank_name  by PaymentBankAccountsTable.bank_name
    var branch_code  by PaymentBankAccountsTable.branch_code
    var branch_name  by PaymentBankAccountsTable.branch_name
    var account_type  by PaymentBankAccountsTable.account_type
    var account_number  by PaymentBankAccountsTable.account_number
    var account_holder  by PaymentBankAccountsTable.account_holder
    override var createdAt by PaymentBankAccountsTable.createdAt
    override var updatedAt by PaymentBankAccountsTable.updatedAt
}

fun PaymentBankAccountsEntity.toPaymentBankAccounts() = PaymentBankAccounts(
    payment_id = this.payment_id,
    bank_code = this.bank_code,
    bank_name = this.bank_name,
    branch_code = this.branch_code,
    branch_name = this.branch_name,
    account_type = this.account_type,
    acount_number = this.account_number,
    acount_holder = this.account_holder,
)
