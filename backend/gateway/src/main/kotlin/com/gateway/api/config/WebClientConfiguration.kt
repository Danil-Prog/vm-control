package com.gateway.api.config

import com.gateway.api.exception.ClientException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Configuration
class WebClientConfiguration {
    @Bean
    fun webClientBuilder() = WebClient.builder()
        .filter(errorHandler())

    companion object {
        fun errorHandler(): ExchangeFilterFunction {
            return ExchangeFilterFunction.ofResponseProcessor { clientResponse ->
                if (clientResponse.statusCode().is4xxClientError) {
                    clientResponse.bodyToMono(String::class.java).flatMap { error ->
                        Mono.error(ClientException(error))
                    }
                } else {
                    Mono.just(clientResponse)
                }
            }
        }
    }
}