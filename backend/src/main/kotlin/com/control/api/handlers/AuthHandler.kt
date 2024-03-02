package com.control.api.handlers

import com.control.api.exception.http.UnauthorizedRequestException
import com.control.api.response.AuthenticationResponse
import com.control.api.service.JwtService
import com.control.api.utils.HttpConstants.Companion.REFRESH_TOKEN
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.time.Duration

@Component
class AuthHandler(private val jwtService: JwtService) {

    suspend fun refreshAuthToken(request: ServerRequest): ServerResponse {
        val refresh = request.cookies().getFirst(REFRESH_TOKEN)?.value
            ?: throw UnauthorizedRequestException("Refresh token is missing request header ")

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

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}