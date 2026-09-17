package com.anderson.banking.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "accounts")
class AccountEntity(

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:Column(name = "holder_name", nullable = false, length = 120)
    var holderName: String = "",

    @field:Column(nullable = false, length = 4)
    var branch: String = "0001",

    @field:Column(
        name = "account_number",
        nullable = false,
        unique = true,
        length = 36
    )
    var accountNumber: String = "",

    @field:Column(nullable = false, precision = 19, scale = 2)
    var balance: BigDecimal = BigDecimal("0.00"),

    @field:Column(nullable = false, length = 3)
    var currency: String = "BRL"
)