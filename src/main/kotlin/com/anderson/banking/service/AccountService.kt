package com.anderson.banking.service

import com.anderson.banking.dto.request.CreateAccountRequest
import com.anderson.banking.dto.response.AccountResponse
import com.anderson.banking.entity.AccountEntity
import com.anderson.banking.repository.AccountRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class AccountService(
    private val repository: AccountRepository
) {

    @Transactional
    fun create(request: CreateAccountRequest): AccountResponse {
        val account = AccountEntity(
            holderName = request.holderName.trim(),
            accountNumber = UUID.randomUUID().toString()
        )

        return repository.save(account).toResponse()
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): AccountResponse {
        val account = repository.findById(id)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Conta não encontrada"
                )
            }

        return account.toResponse()
    }

    private fun AccountEntity.toResponse() = AccountResponse(
        id = requireNotNull(id),
        holderName = holderName,
        branch = branch,
        accountNumber = accountNumber,
        balance = balance,
        currency = currency
    )
}