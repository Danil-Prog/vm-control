package com.gateway.api.clients

import com.gateway.api.model.User
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class UserClient(private val client: WebClient) {

    fun findUserByUsername(username: String): Mono<User> {
        return client.get()
            .uri("/$username")
            .retrieve()
            .bodyToMono(User::class.java)
    }
}