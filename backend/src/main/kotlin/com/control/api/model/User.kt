package com.control.api.model

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.security.core.GrantedAuthority

@Entity
@Table(name = "users")
class User : GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null

    @Column(name = "username", nullable = false)
    @field:NotEmpty
    var username: String? = null

    @Column(name = "firstname")
    var firstname: String? = null

    @Column(name = "lastname")
    val lastname: String? = null

    @Column(name = "password", nullable = false)
    @field:NotEmpty
    var password: String? = null

    override fun getAuthority(): String =
        "ROLE_${Role.DEFAULT.name}"

    override fun toString(): String {
        return "User(id=$id, username=$username, firstname=$firstname, lastname=$lastname, password=$password)"
    }

    enum class Role {
        DEFAULT
    }
}