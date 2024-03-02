package com.control.api.handlers


import com.control.api.RequestValidator
import com.control.api.model.User
import com.control.api.service.UserService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class UserHandler(private val userService: UserService, private val requestValidator: RequestValidator) {

    suspend fun getUserByUsername(request: ServerRequest): ServerResponse {
        val username = request.pathVariable("username")
        val user = userService.findByUsername(username)
        return ServerResponse.ok().bodyValueAndAwait(user)
    }

    suspend fun createUser(request: ServerRequest): ServerResponse {
        val user = requestValidator.bodyObject(request, User::class.java)
        return ServerResponse.ok().buildAndAwait()
    }
}