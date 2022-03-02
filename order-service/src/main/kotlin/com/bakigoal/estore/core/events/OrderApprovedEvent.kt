package com.bakigoal.estore.core.events

import com.bakigoal.estore.command.OrderStatus

data class OrderApprovedEvent(
    val orderId: String,
    val orderStatus: OrderStatus
)