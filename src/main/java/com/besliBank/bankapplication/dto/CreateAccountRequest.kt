package com.besliBank.bankapplication.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class CreateAccountRequest(

        @field:Size(min = 1, max = 25, message = "Account names maximum 25 characters, minimum 1 character included")
        @field:NotEmpty(message = "Account Name cannot be empty")
        val accountName:String,
        @field:Size(min = 11, max = 11, message = "TC Number Consists of 11 Digits")
        @field:NotBlank(message = "There cannot be blank in the TC number.")
        @field:NotEmpty(message = "Account Name cannot be empty")
        val tc:String
)
