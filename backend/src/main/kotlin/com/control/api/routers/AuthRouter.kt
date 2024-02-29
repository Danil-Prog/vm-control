package com.control.api.routers

import com.control.api.handlers.AuthHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class AuthRouter {

    @Bean
    fun authRouters(handler: AuthHandler) = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/auth/refresh", handler::refreshAuthToken)
        }
    }
}