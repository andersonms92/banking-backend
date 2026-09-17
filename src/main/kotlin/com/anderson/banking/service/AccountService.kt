package com.anderson.banking.service

import com.anderson.banking.dto.request.CreateAccountRequest
import com.anderson.banking.dto.response.PageResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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

    @Transactional(readOnly = true)
    fun search(
        name: String?,
        page: Int,
        size: Int
    ): PageResponse<AccountResponse> {
        if (page < 0 || size !in 1..100) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "page deve ser maior ou igual a 0 e size deve estar entre 1 e 100"
            )
        }

        val pageable = PageRequest.of(
            page,
            size,
            Sort.by(Sort.Direction.ASC, "id")
        )

        val normalizedName = name?.trim().orEmpty()

        val result = if (normalizedName.isBlank()) {
            repository.findAll(pageable)
        } else {
            repository.findByHolderNameContainingIgnoreCase(
                normalizedName,
                pageable
            )
        }

        return PageResponse(
            content = result.content.map { it.toResponse() },
            page = result.number,
            size = result.size,
            totalElements = result.totalElements,
            totalPages = result.totalPages,
            last = result.isLast
        )
    }
}