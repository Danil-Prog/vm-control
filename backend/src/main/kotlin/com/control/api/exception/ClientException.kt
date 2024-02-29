package com.control.api.exception

class ClientException(override val message: String) : RuntimeException(message) {
}