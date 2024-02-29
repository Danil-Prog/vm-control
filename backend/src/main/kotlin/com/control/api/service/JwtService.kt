package com.control.api.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

import java.util.Date

@Service
class JwtService(
    @Value("\${gateway.jwt.secret}") val secret: String,
    @Value("\${gateway.jwt.refresh}") val refresh: String,
    @Value("\${gateway.jwt.expiration_access_token_in_mills}") val expirationAccessToken: Long,
    @Value("\${gateway.jwt.expiration_refresh_token_in_mills}") val expirationRefreshToken: Long
) {


    fun accessToken(username: String, roles: Array<String>): String {
        return generate(username, expirationAccessToken, roles, secret)
    }

    fun decodeAccessToken(accessToken: String): DecodedJWT {
        return decode(secret, accessToken)
    }

    fun refreshToken(username: String, roles: Array<String>): String {
        return generate(username, expirationRefreshToken, roles, refresh)
    }

    fun decodeRefreshToken(refreshToken: String): DecodedJWT {
        return decode(refresh, refreshToken)
    }

    fun getRolesAccessToken(decodedJWT: DecodedJWT) = decodedJWT.getClaim("role").asList(String::class.java)
        .map { SimpleGrantedAuthority(it) }

    fun getRolesAccessToken(token: String): List<SimpleGrantedAuthority> = getRolesAccessToken(decodeAccessToken(token))
    fun getRolesRefreshToken(token: String): List<SimpleGrantedAuthority> = getRolesAccessToken(decodeRefreshToken(token))

    fun getUsernameAccessToken(token: String): String = decodeAccessToken(token).subject
    fun getUsernameRefreshToken(token: String): String = decodeRefreshToken(token).subject

    private fun generate(username: String, expirationInMillis: Long, roles: Array<String>, signature: String): String {
        return JWT.create()
            .withSubject(username)
            .withExpiresAt(Date(System.currentTimeMillis() + expirationInMillis))
            .withArrayClaim("role", roles)
            .sign(Algorithm.HMAC512(signature.toByteArray()))
    }

    private fun decode(signature: String, token: String): DecodedJWT {
        return JWT.require(Algorithm.HMAC512(signature.toByteArray()))
            .build()
            .verify(token.replace("Bearer ", ""))
    }

    fun valid(token: String): Boolean {
        val decode = decode(refresh, token)
        return decode.expiresAt.after(Date())
    }
}