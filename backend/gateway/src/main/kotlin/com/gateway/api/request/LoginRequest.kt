package com.gateway.api.request

data class LoginRequest(
    val username: String,
    val password: String
)