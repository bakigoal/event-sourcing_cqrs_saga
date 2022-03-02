package com.bakigoal.estore.product.core.error

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ProductServiceErrorHandler {

    @ExceptionHandler(value = [Exception::class])
    fun handleException(e: Exception, webRequest: WebRequest): ResponseEntity<Any> {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val headers = HttpHeaders()
        return errorResponse(status, e, headers)
    }

    private fun errorResponse(status: HttpStatus, e: Exception, headers: HttpHeaders): ResponseEntity<Any> =
        ResponseEntity(
            ErrorResponseDto(
                status = status.reasonPhrase,
                statusCode = status.value(),
                error = e.message
            ),
            headers,
            status
        )
}