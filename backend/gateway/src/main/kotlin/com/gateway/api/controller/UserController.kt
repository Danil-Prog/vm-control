package com.gateway.api.controller

import com.gateway.api.client.UserClient
import com.gateway.api.model.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/user")
class UserController(private val userClient: UserClient) {

    @GetMapping("/username={username}")
    fun getUserByUsername(@PathVariable username: String): Mono<User> {
        return userClient.getUserByUsername(username)
    }
}