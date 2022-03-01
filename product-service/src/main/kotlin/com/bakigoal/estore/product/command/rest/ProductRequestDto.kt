package com.bakigoal.estore.product.command.rest

data class ProductRequestDto(
    var title: String,
    var price: Double = 0.0,
    var quantity: Int = 0
)