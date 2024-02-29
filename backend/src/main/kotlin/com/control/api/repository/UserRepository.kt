package com.control.api.repository

import com.control.api.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository: JpaRepository<User, Long>{

    fun getByUsername(username: String): Optional<User>
    fun deleteUserByUsername(username: String): User
}