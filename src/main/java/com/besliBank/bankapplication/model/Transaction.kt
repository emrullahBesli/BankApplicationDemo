package com.besliBank.bankapplication.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class Transaction @JvmOverloads constructor(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id: String? = "",
        var amount: BigDecimal?,
        var transactionDate: LocalDateTime?,
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "account_id", nullable = false)
        val account: Account?


) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Transaction

                if (id != other.id) return false
                if (amount != other.amount) return false
                if (transactionDate != other.transactionDate) return false
                return account == other.account
        }

        override fun hashCode(): Int {
                var result = id?.hashCode() ?: 0
                result = 31 * result + (amount?.hashCode() ?: 0)
                result = 31 * result + (transactionDate?.hashCode() ?: 0)
                result = 31 * result + account.hashCode()
                return result
        }

        override fun toString(): String {
                return "Transaction(id=$id, amount=$amount, transactionDate=$transactionDate, account=$account)"
        }
}