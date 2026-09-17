package com.anderson.banking.dto.response

import java.math.BigDecimal

data class AccountResponse(
    val id: Long,
    val holderName: String,
    val branch: String,
    val accountNumber: String,
    val balance: BigDecimal,
    val currency: String
)