package com.control.api.routers

import com.control.api.handlers.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class UserRouter {

    @Bean
    fun userRouters(handler: UserHandler) = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("$BASE_URL/{username}", handler::getUserByUsername)
            POST(BASE_URL, handler::createUser)
        }
    }

    companion object {
        private const val BASE_URL = "/api/v1/user"
    }
}