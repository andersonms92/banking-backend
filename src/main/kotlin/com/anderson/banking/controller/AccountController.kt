package com.anderson.banking.controller

import com.anderson.banking.dto.request.CreateAccountRequest
import com.anderson.banking.dto.response.AccountResponse
import com.anderson.banking.dto.response.PageResponse
import com.anderson.banking.service.AccountService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.net.URI

@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val service: AccountService
) {

    @PostMapping
    fun create(
        @Valid @RequestBody request: CreateAccountRequest
    ): ResponseEntity<AccountResponse> {
        val account = service.create(request)

        return ResponseEntity
            .created(URI.create("/api/accounts/${account.id}"))
            .body(account)
    }

    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: Long
    ): AccountResponse {
        return service.findById(id)
    }

    @GetMapping
    fun search(
        @RequestParam(name = "name", required = false)
        name: String?,

        @RequestParam(name = "page", defaultValue = "0")
        page: Int,

        @RequestParam(name = "size", defaultValue = "10")
        size: Int
    ): PageResponse<AccountResponse> {
        return service.search(name, page, size)
    }

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