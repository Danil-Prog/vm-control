package com.control.api.handlers

import com.control.api.service.UserService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class UserHandler(private val userService: UserService) {

    suspend fun getUserByUsername(request: ServerRequest): ServerResponse {
        val username = request.pathVariable("username")
        val user = userService.findByUsername(username)
        return ServerResponse.ok().bodyValueAndAwait(user)
    }
}