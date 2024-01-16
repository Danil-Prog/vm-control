package com.user.api.service

import com.user.api.model.User
import org.springframework.stereotype.Service

@Service
class UserService {

    fun getUserByUsername(username: String): User? {
        val userList = listOf(User("ivan.ivanov", "Ivan", "Ivanov", "#password"))

        return userList.find { user: User -> user.username == username }
    }
}