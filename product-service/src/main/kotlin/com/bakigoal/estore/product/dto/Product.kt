package com.bakigoal.estore.product.dto

data class Product(
    var title: String,
    var price: Double = 0.0,
    var quantity: Int = 0
)