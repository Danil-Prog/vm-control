package com.gateway.api.handlers

import com.gateway.api.response.AuthenticationResponse
import com.gateway.api.service.JwtService
import com.gateway.api.utils.HttpConstants
import com.gateway.api.utils.HttpConstants.Companion.REFRESH_TOKEN
import org.slf4j.Logger
import org.springframework.boot.web.server.Cookie
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.time.Duration

@Component
class AuthHandler(private val logger: Logger, private val jwtService: JwtService) {

    suspend fun refreshAuthToken(request: ServerRequest): ServerResponse {
        val refresh = request.cookies().getFirst(REFRESH_TOKEN)?.value ?: throw RuntimeException("Refresh token is missing request header ")
        val isValid = jwtService.valid(refresh)

        if (isValid) {
            val username = jwtService.getUsernameRefreshToken(refresh)
            val roles = jwtService.getRolesRefreshToken(refresh).map { it.authority }.toTypedArray()

            val accessToken = jwtService.accessToken(username, roles)
            val refreshToken = jwtService.refreshToken(username, roles)

            val authenticationResponse = AuthenticationResponse(accessToken, refreshToken)

            val responseCookie = ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofMillis(jwtService.expirationRefreshToken))
                .domain("localhost")
                .build()

            return ServerResponse.ok().headers {
                it.setBearerAuth(accessToken)
                it.set(REFRESH_TOKEN, refreshToken)
            }.cookies {
                it.set(REFRESH_TOKEN, responseCookie)
            }.bodyValueAndAwait(authenticationResponse)
        }

        logger.error("Refresh token is not valid")
        return ServerResponse.badRequest().bodyValueAndAwait("Error update refresh token")
    }
}