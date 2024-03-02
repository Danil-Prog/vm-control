package com.control.api

import com.control.api.exception.http.RequestBodyException
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.context.support.beans
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest

@Component
class RequestValidator(private val validator: Validator) {

    suspend fun <T> bodyObject(request: ServerRequest, clazz: Class<T>): T = withContext(Dispatchers.IO) {
        return@withContext request.body(BodyExtractors.toMono(clazz)).filter {
            val violation = validator.validate(it)
            if (violation.isNotEmpty()) {
                throw ConstraintViolationException(violation)
            } else
                true
        }.block()
            ?: throw RequestBodyException("Request body not found")
    }

}