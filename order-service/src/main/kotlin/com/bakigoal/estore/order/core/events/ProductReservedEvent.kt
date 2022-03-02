package com.bakigoal.estore.order.core.events

data class ProductReservedEvent(
    val orderId: String,
    var productId: String = ""
)