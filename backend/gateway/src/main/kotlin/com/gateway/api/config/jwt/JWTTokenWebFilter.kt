package com.gateway.api.config.jwt

import com.gateway.api.service.JwtService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.util.context.Context

@Component
class JWTTokenWebFilter(private val jwtService: JwtService) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val token = exchange.jwtAccessToken() ?: return chain.filter(exchange)

        logger.info("Token: $token")
        try {
            val auth = UsernamePasswordAuthenticationToken(
                jwtService.getUsernameAccessToken(token),
                null,
                jwtService.getRolesAccessToken(token)
            )
            val context: Context = ReactiveSecurityContextHolder.withAuthentication(auth)
            return chain.filter(exchange).contextWrite(context)
        } catch (e: Exception) {
            logger.error("JWT exception", e)
            exchange.response.setStatusCode(HttpStatus.UNAUTHORIZED)
        }

        return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.clearContext())
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        fun ServerWebExchange.jwtAccessToken(): String? =
            request.headers.getFirst(AUTHORIZATION)?.let {
                it.ifEmpty { null } }?.substringAfter("Bearer ")
    }
}