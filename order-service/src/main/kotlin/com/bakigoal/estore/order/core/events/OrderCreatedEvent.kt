package com.bakigoal.estore.order.core.events

import com.bakigoal.estore.order.command.OrderStatus

data class OrderCreatedEvent(
    val orderId: String,
    var userId: String = "",
    var productId: String = "",
    var quantity: Int = 0,
    var addressId: String = "",
    var orderStatus: OrderStatus
)