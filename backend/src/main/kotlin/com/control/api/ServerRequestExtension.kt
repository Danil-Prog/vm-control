package com.control.api

import com.control.api.exception.http.RequestBodyException
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest

/**
 * ServerRequest.body extension
 * Валидация request запроса, получения single объекта из mono
 */
suspend fun <T> ServerRequest.body(clazz: Class<T>): T = withContext(Dispatchers.IO) {
    val obj = body(BodyExtractors.toMono(clazz)).block()
        ?: throw RequestBodyException("Request body is empty")

    val validator = Validation.buildDefaultValidatorFactory().validator
    val violation = validator.validate(obj)

    if (violation.isNotEmpty()) {
        throw ConstraintViolationException(violation)
    }

    return@withContext obj
}

