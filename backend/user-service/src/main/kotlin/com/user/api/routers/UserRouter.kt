package com.user.api.routers

import com.user.api.handlers.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class UserRouter {

    @Bean
    fun router(handler: UserHandler) = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            "/api/v1/user".nest {
                GET("/{username}", handler::getUserByUsername)
            }
        }
    }
}