package com.gateway.api.config.handler

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Component
class JWTAuthFailureHandler : ServerAuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        webFilterExchange: WebFilterExchange?,
        exception: AuthenticationException?
    ): Mono<Void> = mono {
        val exchange = webFilterExchange?.exchange ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request")

        log.error(" Oops, authorization handler failure..exception message: ${exception?.message}")

        with(exchange.response) {
            statusCode = HttpStatus.UNAUTHORIZED
            setComplete().awaitFirstOrNull()
        }
    }


    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}