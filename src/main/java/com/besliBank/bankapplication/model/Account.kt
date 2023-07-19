package com.besliBank.bankapplication.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal

@Entity
data class Account @JvmOverloads constructor(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id:String?="",
        val accountName:String,
        var balance:BigDecimal,
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "customer_id", nullable = false)
        val customer:Customer?,
        @OneToMany(mappedBy = "account", fetch = FetchType.LAZY,cascade = [CascadeType.ALL])
        val transaction: Set<Transaction>? = HashSet()
) {
        constructor(name: String,customer: Customer) : this("",name, BigDecimal.ZERO ,customer,HashSet())

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Account

                if (id != other.id) return false
                if (accountName != other.accountName) return false
                if (balance != other.balance) return false
                if (customer != other.customer) return false
                return transaction == other.transaction
        }

        override fun hashCode(): Int {
                var result = id?.hashCode() ?: 0
                result = 31 * result + accountName.hashCode()
                result = 31 * result + balance.hashCode()
                result = 31 * result + customer.hashCode()
                result = 31 * result + transaction.hashCode()
                return result
        }

        override fun toString(): String {
                return "Account(id=$id, accountName='$accountName', balance=$balance, customer=$customer, transaction=$transaction)"
        }

}

