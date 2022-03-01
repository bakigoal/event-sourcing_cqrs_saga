package com.bakigoal.estore.product.core.events

data class ProductCreatedEvent(
    var productId: String = "",
    var title: String = "",
    var price: Double = 0.0,
    var quantity: Int = 0
)