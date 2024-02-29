package com.control.api.handlers

import com.control.api.model.User
import com.control.api.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    suspend fun createUser(request: ServerRequest): ServerResponse {
        val user = withContext(Dispatchers.IO) {
            request.bodyToMono(User::class.java).block()
        }
        val savedUser = userService.saveUser(user!!)
        return ServerResponse.ok().bodyValueAndAwait(savedUser)
    }
}