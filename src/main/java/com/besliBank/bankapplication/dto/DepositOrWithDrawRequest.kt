package com.besliBank.bankapplication.dto

import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class DepositOrWithDrawRequest @JvmOverloads constructor(
        val id:String="",
        @field:Positive
        val amount:BigDecimal

)
