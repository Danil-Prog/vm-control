package com.user.api.controller

import com.user.api.model.User
import com.user.api.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(private val userService: UserService) {

    @GetMapping("/username={username}")
    fun getUserByUsername(@PathVariable username: String): ResponseEntity<User> {
        return ResponseEntity.ok().body(userService.getUserByUsername(username))
    }
}