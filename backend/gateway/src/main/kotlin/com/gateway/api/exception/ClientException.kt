package com.gateway.api.exception

class ClientException(override val message: String) : RuntimeException(message) {
}