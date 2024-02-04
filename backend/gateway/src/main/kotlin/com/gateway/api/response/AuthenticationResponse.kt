package com.gateway.api.response

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.io.Serializable

data class AuthenticationResponse(val token: String, val refresh: String) : Serializable {

    fun toByteArray(): ByteArray =
        mapper.writeValueAsBytes(this)

    companion object {
        private val mapper = ObjectMapper()
    }
}