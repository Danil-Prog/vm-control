package com.control.api.config.handler

import com.control.api.exception.HttpExceptionFactory.unauthorized
import com.control.api.exception.UnauthorizedRequestException
import com.control.api.response.AuthenticationResponse
import com.control.api.service.JwtService
import com.control.api.utils.HttpConstants.Companion.REFRESH_TOKEN
import kotlinx.coroutines.reactor.mono
import org.springframework.http.MediaType
import org.springframework.http.ResponseCookie
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class JWTAuthSuccessHandler(private val jwtService: JwtService) : ServerAuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        webFilterExchange: WebFilterExchange?,
        authentication: Authentication?
    ): Mono<Void> = mono {
        val principal =
            authentication?.principal ?: throw unauthorized()

        when (principal) {
            is User -> {
                val roles = principal.authorities.map { it.authority }.toTypedArray()

                val accessToken = jwtService.accessToken(principal.username, roles)
                val refreshToken = jwtService.refreshToken(principal.username, roles)

                val exchange = webFilterExchange?.exchange ?: throw unauthorized()

                val authenticationResponse = AuthenticationResponse(accessToken, refreshToken).toByteArray()
                val data = exchange.response.bufferFactory().wrap(authenticationResponse)

                val responseCookie = ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(Duration.ofMillis(jwtService.expirationRefreshToken))
                    .domain("localhost")
                    .build()

                with(exchange.response) {
                    headers.setBearerAuth(accessToken)
                    headers.set(REFRESH_TOKEN, refreshToken)
                    headers.contentType = MediaType.APPLICATION_JSON
                }

                exchange.response.cookies.set(REFRESH_TOKEN, responseCookie)
                exchange.response.writeWith(Mono.just(data)).block()
            }

            else -> throw UnauthorizedRequestException("An error occurred while authenticating the user")
        }

        return@mono null
    }


}