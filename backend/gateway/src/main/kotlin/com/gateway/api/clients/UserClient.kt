package com.gateway.api.clients

import com.gateway.api.model.User
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class UserClient(private val client: WebClient.Builder) {

    fun findUserByUsername(username: String): Mono<User> {
        return userService()
            .get()
            .uri("/$username")
            .retrieve()
            .bodyToMono(User::class.java)
    }

    private fun userService(): WebClient =
        client.baseUrl("http://$USER_SERVICE_HOST:$USER_SERVICE_PORT/$USER_SERVICE_PATH")
            .build()

    companion object {
        private const val USER_SERVICE_HOST = "user-service"
        private const val USER_SERVICE_PORT = 8282
        private const val USER_SERVICE_PATH = "/api/v1/user"

    }
}