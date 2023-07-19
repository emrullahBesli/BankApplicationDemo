package com.besliBank.bankapplication.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionDto(
        val amount: BigDecimal,
        val transactionDate: LocalDateTime,
)

