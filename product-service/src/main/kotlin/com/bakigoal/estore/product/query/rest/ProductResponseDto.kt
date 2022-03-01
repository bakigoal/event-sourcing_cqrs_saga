package com.bakigoal.estore.product.query.rest

data class ProductResponseDto(
    var title: String,
    var price: Double = 0.0,
    var quantity: Int = 0
)