package com.anderson.banking.controller

import com.anderson.banking.dto.response.AccountResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/accounts")
class AccountController {

    @GetMapping("/demo")
    fun getDemoAccount(): AccountResponse {
        return AccountResponse(
            id = 1L,
            holderName = "Anderson Matos",
            branch = "0001",
            accountNumber = "12345-6",
            balance = BigDecimal("1500.00"),
            currency = "BRL"
        )
    }
}