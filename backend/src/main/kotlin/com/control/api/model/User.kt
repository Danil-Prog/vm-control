package com.control.api.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "username", nullable = false)
    val username: String? = null,

    @Column(name = "firstname", nullable = false)
    val firstname: String? = null,

    @Column(name = "lastname", nullable = false)
    val lastname: String? = null,

    @Column(name = "password", nullable = false)
    var password: String? = null,
) : GrantedAuthority {


    override fun getAuthority(): String =
        "ROLE_${Role.DEFAULT.name}"

    enum class Role {
        DEFAULT
    }
}