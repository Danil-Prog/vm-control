package com.control.api.exception

class UserNotFoundException(override val message: String?): RuntimeException(message)