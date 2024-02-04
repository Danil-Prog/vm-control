package com.gateway.api.handlers

import com.gateway.api.response.AuthenticationResponse
import com.gateway.api.service.JwtService
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class AuthHandler(private val logger: Logger, private val jwtService: JwtService) {

    suspend fun refreshAuthToken(request: ServerRequest): ServerResponse {
        val refresh = request.headers().firstHeader(REFRESH_TOKEN) ?: throw RuntimeException("Refresh token is missing request header ")
        val isValid = jwtService.valid(refresh)
        
        if (isValid) {
            val username = jwtService.getUsernameRefreshToken(refresh)
            val roles = jwtService.getRolesRefreshToken(refresh).map { it.authority }.toTypedArray()

            val accessToken = jwtService.accessToken(username, roles)
            val refreshToken = jwtService.refreshToken(username, roles)

            val authenticationResponse = AuthenticationResponse(accessToken, refreshToken)

            return ServerResponse.ok().headers {
                it.setBearerAuth(accessToken)
                it.set(REFRESH_TOKEN, refreshToken)
            }.bodyValueAndAwait(authenticationResponse)
        }

        logger.error("Refresh token is not valid")
        return ServerResponse.badRequest().bodyValueAndAwait("Error update refresh token")
    }

    companion object {
        private const val REFRESH_TOKEN = "Refresh-Token"
    }
}