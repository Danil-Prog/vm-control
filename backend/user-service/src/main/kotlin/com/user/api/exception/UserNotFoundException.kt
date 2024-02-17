package com.user.api.exception

class UserNotFoundException(override val message: String?): RuntimeException(message)