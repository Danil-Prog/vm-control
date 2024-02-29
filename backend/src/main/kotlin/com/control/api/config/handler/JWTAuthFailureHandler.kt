package com.control.api.config.handler

import com.control.api.exception.HttpExceptionFactory.badRequest
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class JWTAuthFailureHandler : ServerAuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        webFilterExchange: WebFilterExchange?,
        exception: AuthenticationException?
    ): Mono<Void> = mono {
        val exchange = webFilterExchange?.exchange ?: throw badRequest()

        logger.error("Oops, authorization handler failure..exception message: ${exception?.message}")

        with(exchange.response) {
            statusCode = HttpStatus.UNAUTHORIZED
            setComplete().awaitFirstOrNull()
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}