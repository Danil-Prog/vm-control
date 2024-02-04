package com.gateway.api.exception

import com.auth0.jwt.exceptions.TokenExpiredException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler
    fun handleTokenExpiredException(exception: TokenExpiredException): ResponseEntity<String> {
        return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
    }
}