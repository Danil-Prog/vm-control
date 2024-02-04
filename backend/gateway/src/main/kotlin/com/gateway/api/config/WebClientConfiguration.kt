package com.gateway.api.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfiguration(
    @Value("\${gateway.user_service.url}") val url: String,
) {

    private final var port = 8282
    private final var api = "/api/v1/user"

    @Bean
    fun webClient() = WebClient.builder()
        .baseUrl("http://$url:$port/$api")
        .build()
}