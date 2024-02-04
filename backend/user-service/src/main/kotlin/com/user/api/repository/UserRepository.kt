package com.user.api.repository

import com.user.api.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository: JpaRepository<User, Long>{

    fun getUserByUsername(username: String): Optional<User>
}