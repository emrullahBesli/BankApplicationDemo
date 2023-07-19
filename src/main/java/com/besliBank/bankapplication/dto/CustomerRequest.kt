package com.besliBank.bankapplication.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class CustomerRequest(
        @field:NotEmpty(message = "Name cannot be empty")
        val name: String,
        @field:NotEmpty(message = "SurName cannot be empty")
        val surName: String,
        @field: Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid e-mail address")
        @field:NotEmpty(message = "Email cannot be empty")
        val email:String,
        @field:Size(min = 11, max = 11, message = "TC Number Consists of 11 Digits")
        @field:NotBlank(message = "There cannot be blank in the TC number")
        @field:NotEmpty(message = "Tc cannot be empty")
        val tc: String
)