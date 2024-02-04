package com.gateway.api.routers

import com.gateway.api.handlers.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class UserRouter {

    @Bean
    fun userRouters(handler: UserHandler) = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/api/v1/user/{username}", handler::getUserByUsername)
        }
    }
}