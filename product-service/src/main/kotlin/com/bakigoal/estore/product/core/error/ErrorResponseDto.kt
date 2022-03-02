package com.bakigoal.estore.product.core.error

data class ErrorResponseDto(
    val status: String,
    val statusCode: Int,
    val error: String?
)