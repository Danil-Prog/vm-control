package com.control.api.config.jwt

import com.control.api.request.LoginRequest
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.core.ResolvableType
import org.springframework.http.MediaType
import org.springframework.http.codec.json.AbstractJackson2Decoder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JWTConverter(private val jacksonDecoder: AbstractJackson2Decoder) : ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange?): Mono<Authentication> = mono {
        val loginRequest = getUsernameAndPassword(exchange!!) ?: throw RuntimeException("Bad request :(")

        return@mono UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
    }

    private suspend fun getUsernameAndPassword(exchange: ServerWebExchange): LoginRequest? {
        val data = exchange.request.body
        val type = ResolvableType.forType(LoginRequest::class.java)

        return jacksonDecoder
            .decodeToMono(data, type, MediaType.APPLICATION_JSON, mapOf())
            .onErrorResume { Mono.empty<LoginRequest>() }
            .cast(LoginRequest::class.java)
            .awaitFirstOrNull()
    }


}