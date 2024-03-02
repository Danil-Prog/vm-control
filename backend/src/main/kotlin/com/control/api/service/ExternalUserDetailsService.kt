package com.control.api.service

import kotlinx.coroutines.reactor.mono
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

typealias ApplicationUser = com.control.api.model.User

@Service
class ExternalUserDetailsService(
    private val userService: UserService
) : ReactiveUserDetailsService {

    override fun findByUsername(username: String?): Mono<UserDetails> = mono {
        val user = userService.findByUsername(username!!).mapToUserDetails()

        logger.info("Username ${user.username}, authorities: ${user.authorities}")
        return@mono user
    }

    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.username)
            .password(this.password)
            .roles(com.control.api.model.User.Role.DEFAULT.name)
            .build()

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}