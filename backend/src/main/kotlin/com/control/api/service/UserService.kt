package com.control.api.service

import com.control.api.exception.UserNotFoundException
import com.control.api.model.User
import com.control.api.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) {

    fun findByUsername(username: String): User {
        return userRepository.getByUsername(username)
            .orElseThrow { UserNotFoundException("Sorry, user not found: $username") }
    }

    fun saveUser(user: User) {
        user.password = passwordEncoder.encode(user.password)
        userRepository.save(user)
    }

    fun deleteUserByUsername(username: String): User {
        return userRepository.deleteUserByUsername(username)
    }

    fun getUserById(id: Long): User {
        return userRepository.findById(id).orElseThrow()
    }
}