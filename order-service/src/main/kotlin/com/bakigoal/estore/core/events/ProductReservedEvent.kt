package com.bakigoal.estore.core.events

data class ProductReservedEvent(
    var productId: String = "",
    var quantity: Int = 0,
    val orderId: String="",
    var userId: String = ""
)