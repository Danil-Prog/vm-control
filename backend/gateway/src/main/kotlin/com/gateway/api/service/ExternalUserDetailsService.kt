package com.gateway.api.service

import com.gateway.api.clients.UserClient
import kotlinx.coroutines.reactor.mono
import org.slf4j.Logger
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

typealias ApplicationUser = com.gateway.api.model.User

@Service
class ExternalUserDetailsService(
    private val userClient: UserClient,
    private val logger: Logger
) : ReactiveUserDetailsService {

    override fun findByUsername(username: String?): Mono<UserDetails> = mono {
        val user = userClient.findUserByUsername(username!!).block()?.mapToUserDetails()
            ?: throw UsernameNotFoundException("User by username: $username, not found")

        logger.info("Username ${user.username}, authorities: ${user.authorities}")
        return@mono user
    }

    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.username)
            .password(this.password)
            .roles(com.gateway.api.model.User.Role.DEFAULT.name)
            .build()
}