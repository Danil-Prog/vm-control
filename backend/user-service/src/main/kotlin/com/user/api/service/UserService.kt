package com.user.api.service

import com.user.api.model.User
import com.user.api.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getUserByUsername(username: String): User {
        return userRepository.getUserByUsername(username).orElseThrow { RuntimeException("Sorry, user not found: $username") }
    }
}