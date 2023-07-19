package com.besliBank.bankapplication.dto

data class CustomerDto(
        val name:String?,
        val surName:String?,
        val email:String?,
        val tc:String?,
        val accounts:Set<CustomerAccountsDto>?
)
