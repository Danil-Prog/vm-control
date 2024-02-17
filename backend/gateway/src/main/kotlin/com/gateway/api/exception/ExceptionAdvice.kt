package com.gateway.api.exception

import com.auth0.jwt.exceptions.TokenExpiredException
import com.gateway.api.response.ResponseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler
    fun handleTokenExpiredException(exception: TokenExpiredException): ResponseEntity<String> {
        return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleUnauthorizedRequestException(exception: UnauthorizedRequestException): ResponseEntity<ResponseException> {
        return ResponseEntity(ResponseException(exception.message!!), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleClientException(exception: ClientException): ResponseEntity<ResponseException> {
        return ResponseEntity(ResponseException(exception.message), HttpStatus.BAD_REQUEST)
    }
}