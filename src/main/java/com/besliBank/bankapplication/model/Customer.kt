package com.besliBank.bankapplication.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator


@Entity
data class Customer @JvmOverloads constructor(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id: String? = "",
        var name: String,
        val surName: String,
        val email: String,
        val tc: String,
        @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
        val accounts: Set<Account>?


) {
    constructor(name: String, surName: String, email: String, tc: String) : this("", name, surName, email, tc, HashSet())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Customer

        if (id != other.id) return false
        if (name != other.name) return false
        if (surName != other.surName) return false
        if (email != other.email) return false
        if (tc != other.tc) return false
        return accounts == other.accounts
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + surName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + tc.hashCode()
        result = 31 * result + accounts.hashCode()
        return result
    }

    override fun toString(): String {
        return "Customer(id=$id, name='$name', surName='$surName', email='$email', tc='$tc', accounts=$accounts)"
    }

}







