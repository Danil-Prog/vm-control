package com.gateway.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfiguration {

    private final var port = 8282
    private final var api = "/api/v1/user"

    @Bean
    fun webClient() = WebClient.builder()
        .baseUrl("http://localhost:$port/$api")
        .build()
}