package com.user.api.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "username", nullable = false)
    val username: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,

    @Column(name = "password", nullable = false)
    val password: String? = null)
