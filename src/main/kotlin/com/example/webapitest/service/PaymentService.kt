package com.example.webapitest.service

import com.example.webapitest.repository.PaymentRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PaymentService(private val paymentRepository: PaymentRepository) {
    suspend fun amount(from: String, to: String) : Int {
        val fromDate = parseStringDate(from)
        val toDate = parseStringDate(to)

        return paymentRepository.sumAmount(fromDate, toDate)
    }

    private fun parseStringDate(value: String): LocalDate {
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(value, dateFormat)
    }

}