package com.gateway.api.handlers

import com.gateway.api.clients.UserClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitPrincipal
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class UserHandler(private val client: UserClient) {

    suspend fun getUserByUsername(request: ServerRequest): ServerResponse {
        val username = request.pathVariable("username")
        val result = withContext(Dispatchers.IO) {
            client.findUserByUsername(username).block()
        } ?: RuntimeException("User not found")
        return ServerResponse.ok().bodyValueAndAwait(result)
    }
}