package com.example.webapitest.model

import java.math.BigDecimal
import java.time.LocalDate

data class Payment(
    val id: Long?,
    val userId: Int,
    val amount: Int,
    val fee: Int,
    val feeRate: BigDecimal,
    val taxRate: Int,
    val billingAmount: Int,
    val transferDate: LocalDate,
    val uploadedDate: LocalDate,
    val status: Int,
)