package com.user.api.handlers

import com.user.api.service.UserService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class UserHandler(private val userService: UserService) {

    suspend fun getUserByUsername(request: ServerRequest): ServerResponse {
        val username = request.pathVariable("username")
        return ServerResponse.ok().bodyValueAndAwait(userService.getUserByUsername(username))
    }
}