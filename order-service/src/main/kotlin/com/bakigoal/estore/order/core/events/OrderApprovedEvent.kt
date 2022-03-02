package com.bakigoal.estore.order.core.events

data class OrderApprovedEvent(
    val orderId: String,
    var productId: String = ""
)