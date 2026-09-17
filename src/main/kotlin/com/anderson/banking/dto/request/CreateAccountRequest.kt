package com.anderson.banking.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateAccountRequest(

    @field:NotBlank
    @field:Size(max = 120)
    val holderName: String
)