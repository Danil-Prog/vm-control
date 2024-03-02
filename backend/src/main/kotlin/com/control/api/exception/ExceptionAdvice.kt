package com.control.api.exception

import com.auth0.jwt.exceptions.TokenExpiredException
import com.control.api.exception.http.RequestBodyException
import com.control.api.exception.http.UnauthorizedRequestException
import com.control.api.exception.http.UserNotFoundException
import com.control.api.response.ResponseException
import com.control.api.response.ValidationErrorResponse
import com.control.api.response.Violation
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler
    fun handleValidationException(exception: ConstraintViolationException): ResponseEntity<ValidationErrorResponse> {
        val violations = exception.constraintViolations.map {
            Violation(
                it.propertyPath.toString(),
                it.message
            )
        }
        return ResponseEntity(ValidationErrorResponse(violations), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleTokenExpiredException(exception: TokenExpiredException): ResponseEntity<String> {
        return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleUnauthorizedRequestException(exception: UnauthorizedRequestException): ResponseEntity<ResponseException> {
        return ResponseEntity(ResponseException(exception.message!!), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleUserNotFoundException(exception: UserNotFoundException): ResponseEntity<ResponseException> {
        return ResponseEntity(ResponseException(exception.message), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleRequestBodyException(exception: RequestBodyException): ResponseEntity<ResponseException> {
        return ResponseEntity(ResponseException(exception.message), HttpStatus.BAD_REQUEST)
    }
}