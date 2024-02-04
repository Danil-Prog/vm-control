package com.gateway.api.model

import org.springframework.security.core.GrantedAuthority

data class User(
    val id: Long,
    val username: String,
    val firstname: String,
    val lastname: String,
    val password: String
) : GrantedAuthority {
    override fun getAuthority(): String =
        "ROLE_${Role.DEFAULT.name}"

    enum class Role {
        DEFAULT
    }
}