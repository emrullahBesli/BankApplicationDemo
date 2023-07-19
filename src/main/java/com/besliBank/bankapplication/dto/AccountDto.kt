package com.besliBank.bankapplication.dto

import java.math.BigDecimal

data class AccountDto(
        val accountName:String,
        var balance: BigDecimal = BigDecimal.ZERO,
        val customer: AccountCustomerDto,
        val transaction:Set<TransactionDto>
)
