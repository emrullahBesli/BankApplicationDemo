package com.besliBank.bankapplication.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class TransferMoneyRequest(
        @field:NotEmpty(message = "Account Id  cannot be empty")
        val accountId:String,
        @field:NotEmpty(message = "Account Id cannot  be empty")
        val targetAccountId:String,
        @field:Positive
        val amount:BigDecimal,
)
