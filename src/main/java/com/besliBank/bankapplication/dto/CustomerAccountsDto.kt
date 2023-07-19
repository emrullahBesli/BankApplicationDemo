package com.besliBank.bankapplication.dto

import java.math.BigDecimal

data class CustomerAccountsDto(
        val accountName:String,
        val balance: BigDecimal = BigDecimal.ZERO,
        var transaction:Set<TransactionDto>
)
