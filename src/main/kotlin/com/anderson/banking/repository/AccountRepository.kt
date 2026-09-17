package com.anderson.banking.repository

import com.anderson.banking.entity.AccountEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<AccountEntity, Long> {

    fun findByHolderNameContainingIgnoreCase(
        name: String,
        pageable: Pageable
    ): Page<AccountEntity>
}