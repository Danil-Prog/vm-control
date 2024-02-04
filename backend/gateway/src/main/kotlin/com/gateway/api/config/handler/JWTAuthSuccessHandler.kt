package com.gateway.api.config.handler

import com.gateway.api.response.AuthenticationResponse
import com.gateway.api.service.JwtService
import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Component
class JWTAuthSuccessHandler(private val jwtService: JwtService) : ServerAuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        webFilterExchange: WebFilterExchange?,
        authentication: Authentication?
    ): Mono<Void> = mono {
        val principal =
            authentication?.principal ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized")

        when (principal) {
            is User -> {
                val roles = principal.authorities.map { it.authority }.toTypedArray()

                val accessToken = jwtService.accessToken(principal.username, roles)
                val refreshToken = jwtService.refreshToken(principal.username, roles)

                val exchange = webFilterExchange?.exchange ?: throw ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Unauthorized"
                )

                val authenticationResponse = AuthenticationResponse(accessToken, refreshToken).toByteArray()
                val data = exchange.response.bufferFactory().wrap(authenticationResponse)

                with(exchange.response) {
                    headers.setBearerAuth(accessToken)
                    headers.set("Refresh-Token", refreshToken)
                    headers.contentType = MediaType.APPLICATION_JSON
                }

                exchange.response.writeWith(Mono.just(data)).block()
            }

            else -> throw RuntimeException("Not user!")
        }

        return@mono null
    }


}