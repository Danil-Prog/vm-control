package com.control.api.exception.http

class UserNotFoundException(override val message: String): RuntimeException(message)