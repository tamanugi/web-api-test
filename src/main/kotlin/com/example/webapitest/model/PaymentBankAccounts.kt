package com.example.webapitest.model

data class PaymentBankAccounts(
    val payment_id: Long,
    val bank_code: String,
    val bank_name: String,
    val branch_code: String,
    val branch_name: String,
    val account_type: Int,
    val acount_number: String,
    val acount_holder: String,
)